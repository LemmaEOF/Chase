package space.bbkr.chase.serialization;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import java.util.stream.Stream;

//TODO: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
public class TomlOps implements DynamicOps<Object> {
	public static final TomlOps INSTANCE = new TomlOps();

	private TomlOps() { }

	@Override
	public Object empty() {
		return TomlNull.INSTANCE;
	}

	@Override
	public <U> U convertTo(DynamicOps<U> outOps, Object input) {
		return null;
	}

	@Override
	public DataResult<Number> getNumberValue(Object input) {
		if (input instanceof Number) {
			return DataResult.success((Number) input);
		}
		return DataResult.error("Not a number: " + input);
	}

	@Override
	public Object createNumeric(Number i) {
		return i;
	}

	@Override
	public DataResult<String> getStringValue(Object input) {
		return null;
	}

	@Override
	public Object createString(String value) {
		return value;
	}

	@Override
	public DataResult<Object> mergeToList(Object list, Object value) {
		return null;
	}

	@Override
	public DataResult<Object> mergeToMap(Object map, Object key, Object value) {
		return null;
	}

	@Override
	public DataResult<Stream<Pair<Object, Object>>> getMapValues(Object input) {
		return null;
	}

	@Override
	public Object createMap(Stream<Pair<Object, Object>> map) {
		return null;
	}

	@Override
	public DataResult<Stream<Object>> getStream(Object input) {
		return null;
	}

	@Override
	public Object createList(Stream<Object> input) {
		return null;
	}

	@Override
	public Object remove(Object input, String key) {
		return null;
	}
}
