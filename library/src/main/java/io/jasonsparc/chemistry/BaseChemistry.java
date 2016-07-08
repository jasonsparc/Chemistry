package io.jasonsparc.chemistry;

/**
 * Created by jason on 08/07/2016.
 */
public abstract class BaseChemistry extends Chemistry {
	protected final Chemistry base;

	protected BaseChemistry(Chemistry base) {
		this.base = base;
	}
}
