public class FactoryConnections implements FactoryForConnections{

	private static FactoryConnections fc;

	private FactoryConnections() {
		
	}
	
	public static FactoryConnections getFC() {
		if (fc == null) {
			fc = new FactoryConnections();
		}
		return fc;
	}

	@Override
	public Connection createConnection(int connectionIndex, int indx1, int indx2, int x1, int y1, int x2, int y2) {
		return (new Connection(connectionIndex, indx1, indx2, x1, y1, x2, y2));
	}	
}
