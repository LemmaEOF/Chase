package space.bbkr.chase.lang.api.primitive;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import space.bbkr.chase.lang.api.ChaseObject;
import space.bbkr.chase.lang.api.type.ChaseType;

public class ChaseIdentifier implements ChaseObject {
	private final Identifier value;

	public ChaseIdentifier(Identifier value) {
		this.value = value;
	}

	@Override
	public ChaseType getType() {
		return ChaseType.IDENTIFIER;
	}

	@Nullable
	@Override
	public Identifier getLiteralValue() {
		return value;
	}

	//TODO: identifier methods

	@Override
	public String toString() {
		return value.toString();
	}
}
