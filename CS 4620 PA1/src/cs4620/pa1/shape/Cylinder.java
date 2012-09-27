package cs4620.pa1.shape;

import java.util.ArrayList;
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
		ArrayList<Float> totalVertices = new ArrayList<Float>();
		ArrayList<Float> topVs = new ArrayList<Float>();
		ArrayList<Float> topNs = new ArrayList<Float>();
		ArrayList<Float> bottomVs = new ArrayList<Float>();
		ArrayList<Float> bottomNs = new ArrayList<Float>();
		ArrayList<Float> totalNormals = new ArrayList<Float>();
		
		//putting in the vertices and their corresponding normals
		totalVertices.add(0.0f);
		topVs.add(0.0f);
		totalVertices.add(1.0f);
		topVs.add(1.0f);
		totalVertices.add(0.0f);
		topVs.add(0.0f);
		totalNormals.add(0.0f);
		topNs.add(0.0f);
		totalNormals.add(1.0f);
		topNs.add(1.0f);
		totalNormals.add(0.0f);
		topNs.add(0.0f);
 		
		for(float theta = 0; theta < 2*Math.PI; theta += radial_interval){
			Point3f p = cylToCar(1.0f, theta, 1.0f);
			Point3f p2 = cylToCar(1.0f, theta + 0.5f*radial_interval, -1.0f);
			
			totalVertices.add(p.x);
			topVs.add(p.x);
			totalVertices.add(p.y);
			topVs.add(p.y);
			totalVertices.add(p.z);
			topVs.add(p.z);
			totalNormals.add(p.x / (float)Math.sqrt((p.x*p.x + 1 + p.z*p.z)));
			topNs.add(p.x / (float)Math.sqrt((p.x*p.x + 1 + p.z*p.z)));
			totalNormals.add(1.0f /(float)Math.sqrt((p.x*p.x + 1 + p.z*p.z)));
			topNs.add(1.0f /(float)Math.sqrt((p.x*p.x + 1 + p.z*p.z)));
			totalNormals.add(p.z /(float)Math.sqrt((p.x*p.x + 1 + p.z*p.z)));
			topNs.add(p.z /(float)Math.sqrt((p.x*p.x + 1 + p.z*p.z)));
			
			totalVertices.add(p2.x);
			bottomVs.add(p2.x);
			totalVertices.add(p2.y);
			bottomVs.add(p2.y);
			totalVertices.add(p2.z); // we need to set the normals here as well.
			bottomVs.add(p2.z);
			totalNormals.add(p2.x /(float)Math.sqrt((p2.x*p2.x + 1 + p2.z*p2.z)));
			bottomNs.add(p2.x /(float)Math.sqrt((p2.x*p2.x + 1 + p2.z*p2.z)));
			totalNormals.add(-1.0f /(float)Math.sqrt((p2.x*p2.x + 1 + p2.z*p2.z)));
			bottomNs.add(-1.0f /(float)Math.sqrt((p2.x*p2.x + 1 + p2.z*p2.z)));
			totalNormals.add(p2.z /(float)Math.sqrt((p2.x*p2.x + 1 + p2.z*p2.z)));
			bottomNs.add(p2.z /(float)Math.sqrt((p2.x*p2.x + 1 + p2.z*p2.z)));
		}
		
		//setting the endpoint
		totalVertices.add(0.0f);
		bottomVs.add(0.0f);
		totalVertices.add(-1.0f);
		bottomVs.add(-1.0f);
		totalVertices.add(0.0f);
		bottomVs.add(0.0f);
		totalNormals.add(0.0f);
		bottomNs.add(0.0f);
		totalNormals.add(-1.0f);
		bottomNs.add(-1.0f);
		totalNormals.add(0.0f);
		bottomNs.add(0.0f);
		
		float[] vertices = new float[totalVertices.size()];
		float[] normals = new float[totalNormals.size()];
		for(int i = 0; i < totalVertices.size(); i++){
			vertices[i] = totalVertices.get(i);
			normals[i] = totalNormals.get(i);
		}
		
		ArrayList<Integer> triangleAL = new ArrayList<Integer>();
		//top circle:
		System.out.println("vertices in top circle: " + topVs.size());
		for(int i = 3; i < topVs.size(); i += 3){
			triangleAL.add(0);
			triangleAL.add(getIndexOf(vertices, topVs.get(i)));
			if(i + 3 >= topVs.size()){
				triangleAL.add(getIndexOf(vertices, topVs.get(3)));
				break;
			} else{
				System.out.println(i);
				triangleAL.add(getIndexOf(vertices, topVs.get(i + 3)));
			}
		}
		/*//bottom circle:
		for(int i = 0; i < bottomVs.size() - 3; i += 3){
			triangleAL.add(3*vCount - 3);
			triangleAL.add(getIndexOf(vertices, bottomVs.get(i)));
			if(i + 3 >= bottomVs.size() - 3){
				triangleAL.add(getIndexOf(vertices, bottomVs.get(0)));
				break;
			} else
				triangleAL.add(getIndexOf(vertices, bottomVs.get(i + 3)));
		}
		//curved surface:
		for(int i = 3; i < topVs.size(); i += 6){
			triangleAL.add(getIndexOf(vertices, topVs.get(i)));
			triangleAL.add(getIndexOf(vertices, bottomVs.get(i)));
			if(i + 6 >= topVs.size())
				triangleAL.add(getIndexOf(vertices, topVs.get(0)));
			else
				triangleAL.add(getIndexOf(vertices, topVs.get(i + 6)));
			//-------------
			triangleAL.add(getIndexOf(vertices, bottomVs.get(i)));
			triangleAL.add(getIndexOf(vertices, topVs.get(i + 6)));
			if(i + 6 >= bottomVs.size())
				triangleAL.add(getIndexOf(vertices, bottomVs.get(0)));
			else
				triangleAL.add(getIndexOf(vertices, bottomVs.get(i + 6)));
		}*/
		int[] triangles = new int[triangleAL.size()];
		for(int i = 0; i < triangles.length; i++)
			triangles[i] = triangleAL.get(i);
		setMeshData(vertices, normals, triangles);
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
	
	private int getIndexOf(float[] a, float x){
		for(int i = 0; i < a.length; i++){
			if(a[i] == x)
				return i;
		}
		throw new IllegalArgumentException("Element not in array");
		//return -1;
	}
}
