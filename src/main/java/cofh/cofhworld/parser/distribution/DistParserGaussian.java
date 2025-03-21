package cofh.cofhworld.parser.distribution;

import cofh.cofhworld.data.numbers.ConstantProvider;
import cofh.cofhworld.data.numbers.INumberProvider;
import cofh.cofhworld.parser.distribution.base.AbstractStoneDistParser;
import cofh.cofhworld.parser.variables.NumberData;
import cofh.cofhworld.world.distribution.Distribution;
import cofh.cofhworld.world.distribution.DistributionGaussian;
import com.typesafe.config.Config;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

public class DistParserGaussian extends AbstractStoneDistParser {

	private final String[] FIELDS = new String[] { "generator", "cluster-count", "center-height", "spread" };

	@Override
	public String[] getRequiredFields() {

		return FIELDS;
	}

	@Override
	@Nonnull
	protected Distribution getFeature(String featureName, Config genData, WorldGenerator gen, INumberProvider numClusters, boolean retrogen, Logger log) {

		INumberProvider centerHeight = NumberData.parseNumberValue(genData.getValue("center-height"));
		INumberProvider spread = NumberData.parseNumberValue(genData.getValue("spread"));
		INumberProvider rolls = genData.hasPath("smoothness") ? NumberData.parseNumberValue(genData.getValue("smoothness")) : new ConstantProvider(2);

		return new DistributionGaussian(featureName, gen, numClusters, rolls, centerHeight, spread, retrogen);
	}
}
