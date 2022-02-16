public class FactoryCity implements Factory {
	private static FactoryCity fc;

	private FactoryCity() {
	}
	
	/**
	 * This function creates the new object of the class FactoryCity if it 
	 * doesn't exist, else returns already created object.
	 * 
	 * @return fc, object of the class FactoryCity.
	 */
	public static FactoryCity getFC() {
		if (fc == null) {
			fc = new FactoryCity();
		}
		return fc;
	}
	
	@Override
	public City createCity(String name,int index, int cityX, int cityY) {
		return (new City(name, index, cityX, cityY, 20, "#FFFFFF"));
	}
}
