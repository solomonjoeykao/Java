import java.awt.*;
public class Savepaint {
  
	private Color color;
    private Stroke stroke;
    private Shape shape;
    
	public Savepaint(Color color, Stroke stroke,Shape shape){
		this.color = color;
		this.stroke = stroke;
		this.shape = shape;
		
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
  