package space.bbkr.chase.lang.serialization;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import space.bbkr.chase.lang.api.*;
import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.Objects;
import java.util.stream.Stream;

public class ChaseOps implements DynamicOps<ChaseObject> {
	public static final ChaseOps INSTANCE = new ChaseOps();

	private ChaseOps() { }

	@Override
	public ChaseObject empty() {
		return ChaseNull.INSTANCE;
	}

	@Override
	public <U> U convertTo(DynamicOps<U> outOps, ChaseObject input) {
		if (input instanceof ChaseData) {
			return convertMap(outOps, input);
		}
		if (input instanceof ChaseList) {
			return convertList(outOps, input);
		}
		if (input instanceof ChaseNull) {
			return outOps.empty();
		}
		if (input instanceof ChaseString) {
			return outOps.createString((String) input.getLiteralValue());
		}
		if (input instanceof ChaseInt) {
			return outOps.createInt((int) input.getLiteralValue());
		}
		if (input instanceof ChaseFloat) {
			return outOps.createDouble((double) input.getLiteralValue());
		}
		if (input instanceof ChaseBoolean) {
			return outOps.createBoolean((boolean) input.getLiteralValue());
		}
		if (input instanceof ChaseIdentifier) {
			return outOps.createString(input.getLiteralValue().toString());
		}
		return null; //TODO: is there an error handling for this?
	}

	@Override
	public DataResult<Number> getNumberValue(ChaseObject input) {
		if (input.getType() == ChaseType.INT || input.getType() == ChaseType.FLOAT) {
			return DataResult.success((Number) input.getLiteralValue());
		}
		return DataResult.error("Not a number: " + input);
	}

	@Override
	public ChaseObject createNumeric(Number i) {
		if (i instanceof Integer || i instanceof Short || i instanceof Byte ) {
			return new ChaseInt(i.intValue());
		}
		return new ChaseFloat(i.doubleValue());
	}

	@Override
	public DataResult<Boolean> getBooleanValue(ChaseObject input) {
		if (input.getType() == ChaseType.BOOLEAN) {
			return DataResult.success((boolean) input.getLiteralValue());
		}
		return DataResult.error("not a boolean: " + input);
	}

	@Override
	public ChaseObject createBoolean(boolean value) {
		return new ChaseBoolean(value);
	}

	@Override
	public DataResult<String> getStringValue(ChaseObject input) {
		if (input.getType() == ChaseType.STRING) {
			return DataResult.success((String) input.getLiteralValue());
		}
		return DataResult.error("Not a string: " + input);
	}

	@Override
	public ChaseObject createString(String value) {
		return new ChaseString(value);
	}

	@Override
	public DataResult<ChaseObject> mergeToList(ChaseObject list, ChaseObject value) {
		if (!(list instanceof ChaseList) && list != empty()) {
			return DataResult.error("mergeToList called with not a list: " + list, list);
		}

		//TODO: types aaaaaa
		final ChaseList result = new ChaseList();
		if (list != empty() && list instanceof ChaseList) {
			result.addAll((ChaseList) list);
		}
		result.add(value);
		return DataResult.success(result);
	}

	@Override
	public DataResult<ChaseObject> mergeToMap(ChaseObject map, ChaseObject key, ChaseObject value) {
		if (!(map instanceof ChaseData) && map != empty()) {
			return DataResult.error("mergeToMap called with not a map: " + map, map);
		}
		if (!(key instanceof ChaseString)) {
			return DataResult.error("key is not a string: " + key, map);
		}

		final ChaseData output = new ChaseData();
		if (map != empty() && map instanceof ChaseData) {
			((ChaseData) map).forEach(output::put);
		}
		output.put(((ChaseString) key).getLiteralValue(), value);

		return DataResult.success(output);
	}

	@Override
	public DataResult<Stream<Pair<ChaseObject, ChaseObject>>> getMapValues(ChaseObject input) {
		if (!(input instanceof ChaseData)) {
			return DataResult.error("Not a JSON object: " + input);
		}
		return DataResult.success(((ChaseData) input).entrySet().stream().map(entry -> Pair.of(new ChaseString(entry.getKey()), entry.getValue() instanceof ChaseNull ? null : entry.getValue())));
	}

	@Override
	public ChaseObject createMap(Stream<Pair<ChaseObject, ChaseObject>> map) {
		final ChaseData result = new ChaseData();
		map.forEach(p -> result.put((String) p.getFirst().getLiteralValue(), p.getSecond()));
		return result;
	}

	@Override
	public DataResult<Stream<ChaseObject>> getStream(ChaseObject input) {
		if (input instanceof ChaseList) {
			return DataResult.success(((ChaseList) input).stream().map(e -> e instanceof ChaseNull ? null : e));
		}
		return DataResult.error("Not a json array: " + input);
	}

	@Override
	public ChaseObject createList(Stream<ChaseObject> input) {
		final ChaseList result = new ChaseList();
		input.forEach(result::add);
		return result;
	}

	@Override
	public ChaseObject remove(ChaseObject input, String key) {
		if (input instanceof ChaseData) {
			final ChaseData result = new ChaseData();
			((ChaseData) input).entrySet().stream().filter(entry -> !Objects.equals(entry.getKey(), key)).forEach(entry -> result.put(entry.getKey(), entry.getValue()));
			return result;
		}
		return input;
	}
}
