package cofh.cofhworld.parser.distribution.base;

import cofh.cofhworld.data.numbers.ExponentProvider;
import cofh.cofhworld.data.numbers.INumberProvider;
import cofh.cofhworld.data.numbers.world.WorldValueEnum;
import cofh.cofhworld.data.numbers.world.WorldValueProvider;
import cofh.cofhworld.parser.GeneratorData;
import cofh.cofhworld.parser.IDistributionParser;
import cofh.cofhworld.parser.IGeneratorParser.InvalidGeneratorException;
import cofh.cofhworld.parser.variables.NumberData;
import cofh.cofhworld.util.random.WeightedBlock;
import cofh.cofhworld.world.distribution.Distribution;
import com.typesafe.config.Config;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class AbstractDistParser implements IDistributionParser {

	private final String[] FIELDS = new String[] { "generator", "cluster-count" };

	protected final List<WeightedBlock> defaultMaterial;

	protected AbstractDistParser() {

		defaultMaterial = generateDefaultMaterial();
	}

	@Override
	public String[] getRequiredFields() {

		return FIELDS;
	}

	protected abstract List<WeightedBlock> generateDefaultMaterial();

	@Override
	@Nonnull
	public final Distribution getFeature(String featureName, Config genObject, boolean retrogen, Logger log) throws InvalidDistributionException {

		INumberProvider numClusters = NumberData.parseNumberValue(genObject.getValue("cluster-count"), 0, Long.MAX_VALUE);

		if(genObject.hasPath("cluster-half-distance")) {
			numClusters = new ExponentProvider(new WorldValueProvider("SPAWN_DIST"), NumberData.parseNumberValue(genObject.getValue("cluster-half-distance"), 0, Long.MAX_VALUE), numClusters);
		}

		WorldGenerator generator;
		try {
			generator = GeneratorData.parseGenerator(getDefaultGenerator(), genObject, defaultMaterial);
		} catch (InvalidGeneratorException e) {
			log.warn("Invalid generator for '{}' on line {}!", featureName, e.origin().lineNumber());
			throw new InvalidDistributionException("Invalid generator", e.origin()).causedBy(e);
		}

		return getFeature(featureName, genObject, generator, numClusters, retrogen, log);
	}

	@Nonnull
	protected abstract Distribution getFeature(String featureName, Config genObject, WorldGenerator gen, INumberProvider numClusters, boolean retrogen, Logger log);

	protected String getDefaultGenerator() {

		return "cluster";
	}
}
