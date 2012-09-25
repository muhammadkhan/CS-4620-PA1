package cs4620.pa1.shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class Sphere extends TriangleMesh 
{
	private ArrayList<Point3f> point3fs = new ArrayList<Point3f>();
	private ArrayList<Float> verticesAL = new ArrayList<Float>();
	private ArrayList<Float> normalsAL = new ArrayList<Float>();
	private ArrayList<Integer> trianglesAL = new ArrayList<Integer>();
	
	@Override
	public void buildMesh(float tolerance)
	{
		// TODO: (Problem 2) Fill in the code to create a sphere mesh.
		point3fs.add(new Point3f(0.0f, 0.0f, 1.0f));
		for(float phi = (float)Math.atan(tolerance); phi <= Math.PI; phi += Math.atan(tolerance)){
			for(float theta = 0; theta <= 2*Math.PI; theta += (float)Math.atan(tolerance)){
				point3fs.add(sphToCar(1, theta, phi));
			}
		}
		//float[] v = makeVArr();
		//float[] n = makeNArr();
		//int[] t = makeTArr();
		//setMeshData(v, n, t);
	}

	@Override
	public Object getYamlObjectRepresentation()
	{
		Map<Object,Object> result = new HashMap<Object, Object>();
		result.put("type", "Sphere");
		return result;
	}
	
	private Point3f sphToCar(float rho, float theta, float phi){
		float x = (float)(rho*Math.sin(phi)*Math.cos(theta));
		float y = (float)(rho*Math.sin(phi)*Math.sin(theta));
		float z = (float)(rho*Math.cos(phi));
		return new Point3f(x,y,z);
	}
	
	private float[] makeVArr(){
		for(Point3f p : point3fs){
			verticesAL.add(p.x);
			verticesAL.add(p.y);
			verticesAL.add(p.z);
		}
		float[] varr = new float[verticesAL.size()];
		for(int i = 0; i < varr.length; i++)
			varr[i] = verticesAL.get(i);
		return varr;
	}
	
	private float[] makeNArr(){
		for(int i = 0; i < point3fs.size(); i++){
			Point3f p1 = point3fs.get(i);
			Point3f p2;
			if(i + 1 == point3fs.size())
				p2 = point3fs.get(1);
			else
				p2 = point3fs.get(i + 1);
			Vector3f v1 = new Vector3f(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z);
			Vector3f v2 = new Vector3f(-p1.x, -p1.y, -p1.z);
			Vector3f cross = new Vector3f();
			cross.cross(v1, v2);
			cross.normalize(cross);
			normalsAL.add(cross.x);
			normalsAL.add(cross.y);
			normalsAL.add(cross.z);
		}
		float[] narr = new float[normalsAL.size()];
		for(int i = 0; i < narr.length; i++)
			narr[i] = normalsAL.get(i);
		return narr;
	}
	
	private int[] makeTArr(){
		
		return null;
	}
}
