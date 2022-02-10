import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.geometry.VPos;

// https://stackoverflow.com/questions/14882806/center-text-on-canvas

/* A program to draw a spider chart
 * 
 */
public class SpiderChart extends Application{
	
	public double[] polarToXY(double r, double theta) {
		double[] xy = new double[2];
		xy[0] = r * Math.cos(theta);
		xy[1] = r * Math.sin(theta);
		return xy;
	}
	
	public double ithTheta(int n, int i) {
		double thetaStart = 0.5 * Math.PI;			// start at 90 angle
		double thetaDiff = -i * (2 * Math.PI / n);  // counterclockwise
		return thetaStart + thetaDiff;
	}
	
	public double[] adjustCoordsToCanvas(double x, double y, int width, int height) {
		double[] xyCanvas = new double[2];
		xyCanvas[0] = width / 2 + x;
		xyCanvas[1] = height / 2 - y;
		return xyCanvas;
	}
	
	public void drawChart(GraphicsContext g, int width, int height, double[] data) {
		double webStep = width / 10;
		double webR = 4 * webStep;

		// fill background
		g.setFill(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		// draw polygon
		int n = data.length;
		double[] x = new double[n];
		double[] y = new double[n];
		for (int i = 0; i < n; i++) {
			double[] xy = polarToXY(webR * data[i], ithTheta(n, i));
			xy = adjustCoordsToCanvas(xy[0], xy[1], width, height);
			x[i] = xy[0];
			y[i] = xy[1];
		}
		g.setFill(Color.LIGHTBLUE);
		g.fillPolygon(x, y, n);
		
		// draw circles
		
		for (int i = 1; i < 5; i++) {
			double start = webStep * i;
			double end = width - webStep * 2 * i;
			System.out.println(start + " " + end);
			g.strokeOval(start, start, end, end);
		}

		// draw axis
		for (int i = 0; i < n; i++) {
			double[] xy = polarToXY(webR, ithTheta(n, i));
			xy = adjustCoordsToCanvas(xy[0], xy[1], width, height);
			g.strokeLine(width/2, height/2, xy[0], xy[1]);
		}


		// draw label
		g.setTextAlign(TextAlignment.CENTER);
		g.setTextBaseline(VPos.CENTER);
		for (int i = 0; i < n; i++) {
			double[] xy = polarToXY(width/2 - 30, ithTheta(n, i));
			xy = adjustCoordsToCanvas(xy[0], xy[1], width, height);
			g.strokeText(String.valueOf(data[i]), xy[0], xy[1]);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {		
		int width = 500;
		int height = width;
		Canvas canvas = new Canvas(width, height);
		GraphicsContext g = canvas.getGraphicsContext2D();
		
		// dummy data
		double[] data = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6};
		drawChart(g, width, height, data);

		BorderPane root = new BorderPane(canvas);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Spider Chart");
		stage.show();
		stage.setResizable(false);
	}
}
