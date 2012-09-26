package cs4620.pa1.shape;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Point3f;

public class Cylinder extends TriangleMesh
{
	@Override
	public void buildMesh(float tolerance)
	{
		// TODO: (Problem 2) Fill in the code to create a cylinder mesh.
		//radius is one. 
		
		int radial_sectors = (int) Math.ceil(2*Math.PI / tolerance); // this can be changed later.
		float radial_interval = (float)(2*Math.PI * tolerance/7); 
		
		//we include both faces and the two axis points
		int vCount = (radial_sectors * 2) + 2;
		float[] vertices = new float[vCount * 3];
		float[] normals = new float[vCount * 3];
		
		//putting in the vertices and their corresponding normals
		vertices[0] = 0;
		vertices[1] = 1;
		vertices[2] = 0;
		normals[0] = 0;
		normals[1] = 1;
		normals[2] = 0;
 		
		int index = 3;
		for(float theta = 0; theta < 2*Math.PI; theta += radial_interval){
			Point3f p = cylToCar(1.0f, theta, 1.0f);
			Point3f p2 = cylToCar(1.0f, theta + 0.5f*radial_interval, -1.0f);
			
			vertices[index] = p.x;
			vertices[index + 1] = p.y;
			vertices[index + 2] = p.z;
			normals[index] = p.x;
			normals[index + 1] = 0;
			normals[index + 2] = p.z;
			
			vertices[index + 3] = p2.x;
			vertices[index + 4] = p2.y;
			vertices[index + 5] = p2.z; // we need to set the normals here as well.
			normals[index + 3] = p2.x;
			normals[index + 4] = 0;
			normals[index + 5] = p2.z;
		}
		
		//setting the endpoint
		vertices[vCount*3 - 3] = 0;
		vertices[vCount*3 - 2] = -1;
		vertices[vCount*3 - 1] = 0;
		normals[vCount*3 - 3] = 0;
		vertices[vCount*3 - 2] = -1;
		vertices[vCount*3 - 1] = 0;
		
		
		
		
		
		
		
		
		
		
		
		
	}

	@Override
	public Object getYamlObjectRepresentation()
	{
		Map<Object,Object> result = new HashMap<Object, Object>();
		result.put("type", "Cylinder");
		return result;
	}
	
	//this is so you can start at (0,1,1)
	private Point3f cylToCar(float r, float theta, float y){
		float z = (float) ((float)r*Math.cos(theta));
		float x = (float) ((float)r*Math.sin(theta));
		return new Point3f(x, y, z);
	}
}
