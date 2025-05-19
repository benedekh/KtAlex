import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.sp
import kotlin.math.sqrt
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

// Data Model
data class Point(val x: Double, val y: Double)

data class Node(
    val id: String,
    var position: Point, // Position in world coordinates
    val label: String,
    var size: Double = 20.0, // Radius or characteristic size in world units
    val color: Color = Color.Blue
)

data class Edge(
    val id: String,
    val fromNodeId: String,
    val toNodeId: String,
    val label: String? = null,
    val color: Color = Color.Gray,
    var thickness: Double = 2.0 // Thickness in world units
)

class Graph {
    val nodes = mutableMapOf<String, Node>()
    val edges = mutableListOf<Edge>()

    fun addNode(node: Node) {
        nodes[node.id] = node
    }

    fun addEdge(edge: Edge): Edge? {
        if (nodes.containsKey(edge.fromNodeId) && nodes.containsKey(edge.toNodeId)) {
            edges.add(edge)
            return edge
        }
        return null
    }

    fun getNode(id: String): Node? = nodes[id]
}

// ViewModel
class GraphViewerViewModel {
    val graph = Graph()
    var zoomLevel by mutableStateOf(1.0)
        private set
    var panOffset by mutableStateOf(Point(0.0, 0.0)) // World coordinates at the top-left of the view
        private set

    private val minZoom = 0.1
    private val maxZoom = 10.0

    init {
        addSampleData()
    }

    private fun addSampleData() {
        val nodeA = Node(id = "A", position = Point(100.0, 100.0), label = ".Node A")
        val nodeB = Node(id = "B", position = Point(300.0, 200.0), label = ".Node B", color = Color.Red)
        val nodeC = Node(id = "C", position = Point(150.0, 350.0), label = ".Node C", size = 25.0, color = Color.Green)
        val nodeD = Node(id = "D", position = Point(450.0, 300.0), label = ".Node D")
        val nodeE = Node(id = "E", position = Point(250.0, 50.0), label = ".Node E", color = Color.Magenta)


        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addNode(nodeC)
        graph.addNode(nodeD)
        graph.addNode(nodeE)

        graph.addEdge(Edge(id = "e1", fromNodeId = "A", toNodeId = "B", label = "Link A-B"))
        graph.addEdge(Edge(id = "e2", fromNodeId = "A", toNodeId = "C", label = "Connects A-C", thickness = 3.0))
        graph.addEdge(Edge(id = "e3", fromNodeId = "B", toNodeId = "C")) // .Edge without label
        graph.addEdge(Edge(id = "e4", fromNodeId = "C", toNodeId = "D", label = "Path C->D", color = Color(0xFFFFA500))) // Orange
        graph.addEdge(Edge(id = "e5", fromNodeId = "E", toNodeId = "A", label = "Back to A"))
        graph.addEdge(Edge(id = "e6", fromNodeId = "E", toNodeId = "B", label = "E to B", color = Color.Cyan))
    }

    fun worldToScreen(worldPoint: Point, currentZoom: Double, currentPanOffset: Point): Point {
        val screenX = (worldPoint.x - currentPanOffset.x) * currentZoom
        val screenY = (worldPoint.y - currentPanOffset.y) * currentZoom
        return Point(screenX, screenY)
    }

    fun screenToWorld(screenPoint: Point, currentZoom: Double, currentPanOffset: Point): Point {
        val worldX = screenPoint.x / currentZoom + currentPanOffset.x
        val worldY = screenPoint.y / currentZoom + currentPanOffset.y
        return Point(worldX, worldY)
    }

    fun onZoom(zoomFactorChange: Double, mouseScreenPosition: Point) {
        val mouseWorldPosBeforeZoom = screenToWorld(mouseScreenPosition, zoomLevel, panOffset)

        val newZoomLevel = (zoomLevel * zoomFactorChange).coerceIn(minZoom, maxZoom)

        if (newZoomLevel == zoomLevel) return // No change if zoom is at min/max and trying to go further

        val newPanX = mouseWorldPosBeforeZoom.x - (mouseScreenPosition.x / newZoomLevel)
        val newPanY = mouseWorldPosBeforeZoom.y - (mouseScreenPosition.y / newZoomLevel)

        zoomLevel = newZoomLevel
        panOffset = Point(newPanX, newPanY)
    }

    fun onPan(dragDeltaScreen: Point) {
        val panDXWorld = dragDeltaScreen.x / zoomLevel
        val panDYWorld = dragDeltaScreen.y / zoomLevel
        panOffset = Point(panOffset.x - panDXWorld, panOffset.y - panDYWorld)
    }
}

// Composable .GraphView
@Composable
fun GraphView(viewModel: GraphViewerViewModel = remember { GraphViewerViewModel() }) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) { // Pan gesture detection
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    viewModel.onPan(Point(dragAmount.x.toDouble(), dragAmount.y.toDouble()))
                }
            }
            .pointerInput(Unit) { // Zoom gesture detection
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (event.type == PointerEventType.Scroll) {
                            val scrollDeltaY = event.changes.first().scrollDelta.y
                            val mouseScreenPos = event.changes.first().position

                            val zoomFactorChange = if (scrollDeltaY > 0) 1.0 / 1.2 else 1.2

                            viewModel.onZoom(
                                zoomFactorChange,
                                Point(mouseScreenPos.x.toDouble(), mouseScreenPos.y.toDouble())
                            )
                            event.changes.first().consume() // Consume the event to prevent propagation
                        }
                    }
                }
            }
    ) {
        val currentZoom = viewModel.zoomLevel
        val currentPanOffset = viewModel.panOffset

        // Draw Edges
        viewModel.graph.edges.forEach { edge ->
            val nodeFrom = viewModel.graph.getNode(edge.fromNodeId)
            val nodeTo = viewModel.graph.getNode(edge.toNodeId)
            if (nodeFrom != null && nodeTo != null) {
                val startScreenPos = viewModel.worldToScreen(nodeFrom.position, currentZoom, currentPanOffset)
                val endScreenPos = viewModel.worldToScreen(nodeTo.position, currentZoom, currentPanOffset)

                // Scale edge thickness with square root of zoom for less aggressive scaling, ensure minimum thickness
                val strokeWidth = (edge.thickness * sqrt(currentZoom)).toFloat().coerceAtLeast(0.5f)

                drawLine(
                    color = edge.color,
                    start = Offset(startScreenPos.x.toFloat(), startScreenPos.y.toFloat()),
                    end = Offset(endScreenPos.x.toFloat(), endScreenPos.y.toFloat()),
                    strokeWidth = strokeWidth
                )

                edge.label?.let { labelText ->
                    // Scale font size with square root of zoom and clamp it
                    val baseFontSize = 12.sp
                    val scaledFontSize = (baseFontSize.value * sqrt(currentZoom).toFloat()).sp

                    val textLayoutResult = textMeasurer.measure(
                        text = labelText,
                        style = TextStyle(fontSize = scaledFontSize, color = Color.DarkGray)
                    )

                    val midPointX = ((startScreenPos.x + endScreenPos.x) / 2).toFloat()
                    val midPointY = ((startScreenPos.y + endScreenPos.y) / 2).toFloat()

                    // Center the text on the midpoint of the edge
                    val textOffsetX = midPointX - textLayoutResult.size.width / 2
                    // Adjust Y to be slightly above the line, considering stroke width for better placement
                    val textOffsetY = midPointY - textLayoutResult.size.height / 2 - (strokeWidth / 2 + 2f)


                    if (currentZoom > 0.3) { // Hide labels if too zoomed out for clarity
                        drawText(
                            textLayoutResult = textLayoutResult,
                            topLeft = Offset(textOffsetX, textOffsetY)
                        )
                    }
                }
            }
        }

        // Draw Nodes
        viewModel.graph.nodes.values.forEach { node ->
            val screenPos = viewModel.worldToScreen(node.position, currentZoom, currentPanOffset)
            // .Node size (radius) scales directly with zoom level
            val nodeScreenRadius = (node.size * currentZoom).toFloat()

            // Basic culling: only draw if node is reasonably visible
            if (nodeScreenRadius > 0.5f) {
                drawCircle(
                    color = node.color,
                    radius = nodeScreenRadius,
                    center = Offset(screenPos.x.toFloat(), screenPos.y.toFloat())
                )

                // Scale font size for node labels with square root of zoom and clamp it
                val baseFontSize = 14.sp
                val scaledFontSize = (baseFontSize.value * sqrt(currentZoom).toFloat()).sp

                val textLayoutResult = textMeasurer.measure(
                    text = node.label,
                    style = TextStyle(fontSize = scaledFontSize, color = Color.Black)
                )

                // Center text on the node
                val textOffsetX = screenPos.x.toFloat() - textLayoutResult.size.width / 2
                val textOffsetY = screenPos.y.toFloat() - textLayoutResult.size.height / 2

                if (currentZoom > 0.2) { // Hide labels if too zoomed out for clarity
                    drawText(
                        textLayoutResult = textLayoutResult,
                        topLeft = Offset(textOffsetX, textOffsetY)
                    )
                }
            }
        }
    }
}

// Assuming your GraphView composable and its related classes (Point, Node, Edge, Graph, GraphViewerViewModel)
// are in the same package or imported correctly.
// If they are in a different package, you'll need to add the appropriate import statement(s), e.g.:
// import com.example.graphviewer.GraphView

fun main() = application {
    val windowState = rememberWindowState(width = 1024.dp, height = 768.dp)

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Kotlin Graph Viewer"
    ) {
        // You can optionally wrap GraphView with MaterialTheme or other theme providers
        MaterialTheme {
            GraphView() // This instantiates and calls your Graph Viewer
        }
    }
}