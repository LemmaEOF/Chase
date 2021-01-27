package space.bbkr.chase.lang.api;


import net.minecraft.util.Identifier;
import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.HashMap;

public class ChaseData extends HashMap<String, ChaseObject> implements ChaseObject {
	@Override
	public ChaseType getType() {
		return null;
	}

	@Override
	public ChaseData getLiteralValue() {
		return this;
	}
	
	public ChaseObject putInt(String key, int value) {
		return put(key, new ChaseInt(value));
	}

	public int getInt(String key) {
		ChaseObject obj = get(key);
		if (obj.getType().canCastTo(ChaseType.INT)) {
			return (int) obj.getType().cast(obj, ChaseType.INT).getLiteralValue();
		}
		return 0;
	}

	public ChaseObject putFloat(String key, float value) {
		return put(key, new ChaseFloat(value));
	}

	public double getFloat(String key) {
		ChaseObject obj = get(key);
		if (obj.getType().canCastTo(ChaseType.FLOAT)) {
			return (int) obj.getType().cast(obj, ChaseType.FLOAT).getLiteralValue();
		}
		return 0;
	}

	public ChaseObject putString(String key, String value) {
		return put(key, new ChaseString(value));
	}

	public String getString(String key) {
		ChaseObject obj = get(key);
		if (obj.getType().canCastTo(ChaseType.STRING)) {
			return obj.getType().cast(obj, ChaseType.STRING).getLiteralValue().toString();
		}
		return "";
	}

	public ChaseObject putId(String key, Identifier value) {
		return put(key, new ChaseIdentifier(value));
	}

	public Identifier getId(String key) {
		ChaseObject obj = get(key);
		if (obj.getType().canCastTo(ChaseType.IDENTIFIER)) {
			return (Identifier) obj.getType().cast(obj, ChaseType.IDENTIFIER).getLiteralValue();
		}
		return new Identifier(""); //TODO: throw
	}

	public ChaseData getData(String key) {
		ChaseObject obj = get(key);
		if (obj.getType().canCastTo(ChaseType.ANY_DATA)) {
			return (ChaseData) obj.getType().cast(obj, ChaseType.ANY_DATA).getLiteralValue();
		}
		return null; //TODO: throw
	}
}
