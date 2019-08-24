package cofh.cofhworld.parser.generator;

import cofh.cofhworld.data.numbers.ExponentProvider;
import cofh.cofhworld.data.numbers.INumberProvider;
import cofh.cofhworld.data.numbers.world.WorldValueProvider;
import cofh.cofhworld.parser.generator.base.AbstractGenParserClusterCount;
import cofh.cofhworld.parser.variables.NumberData;
import cofh.cofhworld.util.random.WeightedBlock;
import cofh.cofhworld.world.generator.WorldGenMinableCluster;
import cofh.cofhworld.world.generator.WorldGenSparseMinableCluster;
import com.typesafe.config.Config;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.List;

public class GenParserCluster extends AbstractGenParserClusterCount {

	private final boolean sparse;

	public GenParserCluster(boolean sparse) {

		this.sparse = sparse;
	}

	@Override
	@Nonnull
	public WorldGenerator parseGenerator(String name, Config genObject, Logger log, List<WeightedBlock> resList, List<WeightedBlock> matList) throws InvalidGeneratorException {

		INumberProvider clusterSize = NumberData.parseNumberValue(genObject.getValue("cluster-size"), 0, Long.MAX_VALUE);

		if(genObject.hasPath("cluster-half-distance")) {
			clusterSize = new ExponentProvider(new WorldValueProvider("SPAWN_DIST"), NumberData.parseNumberValue(genObject.getValue("cluster-half-distance"), 0, Long.MAX_VALUE), clusterSize);
		}
		
		if (sparse) {
			return new WorldGenSparseMinableCluster(resList, clusterSize, matList);
		}
		return new WorldGenMinableCluster(resList, clusterSize, matList);
	}

}
