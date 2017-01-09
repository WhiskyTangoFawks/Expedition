package wtf.config.ore;

public class OreDefReg {

	public final String stoneList;
	
	public String surfaces;
	public final int[] genRange;
	public final int[] orePerChunk;

	public final boolean denseBlock;

	public int cloudDiameter = 10;

	public int vanillaBlocksPerCluster = 8;

	public int[] veinDimensions = {8,2,2};

	public float veinPitch= 1.5F;

	public String textureLoc = null;

	public int[] dimensionIDs = null;
	
	public int density = 100;

	public String biomeTags = "";
	
	public final String name = "name is good";
	
	public String reqBiomeTypes = "";
	
	public OreDefReg (String stoneList, String genType, int[] genRange, int[] orePerChunk, boolean denseBlock){
		this.stoneList = stoneList;
		this.genRange = genRange;
		this.orePerChunk = orePerChunk;
		this.denseBlock = denseBlock;
	}
	
	public String getSurfaces() {
		return surfaces;
	}

	public OreDefReg setSurfaces(String surfaces) {
		this.surfaces = surfaces;
		return this;
	}


	public OreDefReg setCloudDiameter(int cloudDiameter) {
		this.cloudDiameter = cloudDiameter;
		return this;
	}


	public OreDefReg setVanillaBlocksPerCluster(int vanillaBlocksPerCluster) {
		this.vanillaBlocksPerCluster = vanillaBlocksPerCluster;
		return this;
	}

	public OreDefReg setVeinDimensions(int[] veinDimensions) {
		this.veinDimensions = veinDimensions;
		return this;
	}


	public OreDefReg setVeinPitch(float veinPitch) {
		this.veinPitch = veinPitch;
		return this;
	}

	public OreDefReg setTextureLoc(String textureLoc) {
		this.textureLoc = textureLoc;
		return this;
	}

	public OreDefReg setDimensionIDs(int[] dimensionIDs) {
		this.dimensionIDs = dimensionIDs;
		return this;
	}
	public OreDefReg setDimensionIDs(int dimensionIDs) {
		int[] array = {dimensionIDs};
		this.dimensionIDs = array;
		return this;
	}

	public OreDefReg setDensity(int density) {
		this.density = density;
		return this;
	}

	public OreDefReg setBiomeTags(String string) {
		this.biomeTags = string;
		return this;
	}

	public OreDefReg setReqBiomes(String string){
		this.reqBiomeTypes=string;
		return this;
	}

	
}
