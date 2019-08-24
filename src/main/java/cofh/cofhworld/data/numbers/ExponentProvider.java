package cofh.cofhworld.data.numbers;

import net.minecraft.world.World;

import java.util.Random;

public class ExponentProvider implements INumberProvider {

	protected INumberProvider arg;
	protected INumberProvider half;
	protected INumberProvider init;

	public ExponentProvider(INumberProvider arg, INumberProvider half, INumberProvider init) {
		this.arg = arg;
		this.half = half;
		this.init = init;
	}

	public long longValue(World world, Random rand, DataHolder data) {
		return (long)doubleValue(world, rand, data);
	}

	public double doubleValue(World world, Random rand, DataHolder data) {
		return init.doubleValue(world, rand, data) * Math.pow(0.5, arg.doubleValue(world, rand, data) / half.doubleValue(world, rand, data));
	}

}
