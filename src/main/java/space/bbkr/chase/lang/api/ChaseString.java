package space.bbkr.chase.lang.api;

import org.jetbrains.annotations.Nullable;
import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.ArrayList;
import java.util.List;

public class ChaseString implements ChaseIterable<ChaseString> {
	private final String value;

	public ChaseString(String value) {
		this.value = value;
	}

	private ChaseString(char... value) {
		this.value = new String(value);
	}

	@Override
	public ChaseType getType() {
		return ChaseType.STRING; //TODO: listy stuff in type
	}

	@Nullable
	@Override
	public String getLiteralValue() {
		return value;
	}

	@Override
	public List<ChaseString> getEntries() {
		List<ChaseString> entries = new ArrayList<>();
		for (int i = 0; i < value.length(); i++) {
			entries.add(new ChaseString(value.charAt(i)));
		}
		return entries;
	}

	@Override
	public String toString() {
		return value;
	}
}
