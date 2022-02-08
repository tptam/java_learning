import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/* A program to draw a spider chart
 * 
 */
public class SpiderChart extends Application{
	
	public double[] PolarToXY(double r, double theta) {
		double[] xy = new double[2];
		xy[0] = r * Math.cos(theta);
		xy[1] = r * Math.sin(theta);
		return xy;
	}
	
	public void drawChart(GraphicsContext g, int width, int height, double[] data) {
		// fill background
		g.setFill(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		// draw polygon
		int n = 4;
		double[] x = new double[n];
		double[] y = new double[n];
		for (int i = 0; i < n; i++) {
			double[] xy = PolarToXY(200 * data[i], (i - 1) * 2 * Math.PI / n);
			x[i] = xy[0] + 250;
			y[i] = xy[1] + 250;
		}
		g.setFill(Color.LIGHTBLUE);
		g.fillPolygon(x, y, 4);
		
		// draw circles
		g.strokeOval(50, 50, 400, 400);
		g.strokeOval(100, 100, 300, 300);
		g.strokeOval(150, 150, 200, 200);
		g.strokeOval(200, 200, 100, 100);
		
		// draw axis
		for (int i = 0; i < n; i++) {
			double[] xy = PolarToXY(200, i * 2 * Math.PI / n);
			g.strokeLine(250, 250, xy[0]+250, xy[1]+250);
		}


		// draw label
		g.strokeText(String.valueOf(data[0]), 250, 45);
		g.strokeText(String.valueOf(data[1]), 455, 250);
		g.strokeText(String.valueOf(data[2]), 250, 465);
		g.strokeText(String.valueOf(data[3]), 20, 250);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {		
		int width = 500;
		int height = 500;
		Canvas canvas = new Canvas(500, 500);
		GraphicsContext g = canvas.getGraphicsContext2D();
		// dummy data
		double[] data = {0.5, 0.9, 0.2, 0.6};
		drawChart(g, width, height, data);

		BorderPane root = new BorderPane(canvas);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Spider Chart");
		stage.show();
		stage.setResizable(false);
	}
}
