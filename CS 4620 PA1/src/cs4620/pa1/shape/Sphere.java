package cs4620.pa1.shape;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Point3f;

public class Sphere extends TriangleMesh 
{
	@Override
	public void buildMesh(float tolerance)
	{
		int lats = (int)Math.ceil(2*Math.PI / tolerance);
		int longs = (int)Math.ceil(4*Math.PI / tolerance);
		
		
		int numV = (longs + 1 ) * (lats + 1);
		float[] vertexArr = new float[3*numV];
		float[] normalArr = new float[3*numV];
		
 
		for(int i = 0;i <= lats;i++)
			for(int j = 0;j <= longs;j++){
				float th = (float) (i * Math.PI / lats);
				float ph = (float) (j * 2 * Math.PI / longs);
				Point3f p = sphToCar(1.0f,th,ph);
				int index = j + longs*i;
				
				vertexArr[3*index] = p.x;
				vertexArr[3*index + 1] = p.y;
				vertexArr[3*index + 2] = p.z;
				
				//this is the same because it is a unit sphere.
				normalArr[3*index] = p.x ;
				normalArr[3*index + 1] = p.y;
				normalArr[3*index + 2] = p.z;
			}
		
		int numT = lats * longs * 2;
		int[] triangles = new int[numT*3];
		int n = 0;
		for(int i = 0;i < lats;i++)
		{
			int i_next = i + 1;
			for(int j = 0;j<longs;j++)
			{
				int j_next = j + 1;
				triangles[n] = i*longs + j;
				triangles[n + 1] = i_next*longs+j;
				triangles[n + 2] = i*longs+j_next;
				
				triangles[n + 3] = i*longs+j_next;
				triangles[n + 4] = i_next*longs+j;
				triangles[n + 5] = i_next*longs+j_next;
				n += 6;
			}
		}
		setMeshData(vertexArr, normalArr, triangles);
	}

	@Override
	public Object getYamlObjectRepresentation()
	{
		Map<Object,Object> result = new HashMap<Object, Object>();
		result.put("type", "Sphere");
		return result;
	}
	
	//goes from spherical to cartesian points
	private Point3f sphToCar(float rho, float theta, float phi){
		float x = (float)(rho*Math.sin(phi)*Math.cos(theta));
		float y = (float)(rho*Math.sin(phi)*Math.sin(theta));
		float z = (float)(rho*Math.cos(phi));
		return new Point3f(x,y,z);
	}
}
