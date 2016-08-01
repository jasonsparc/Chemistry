package io.jasonsparc.chemistry.internal.flask_selectors;

import io.jasonsparc.chemistry.Flask;
import io.jasonsparc.chemistry.FlaskSelector;

/**
 * Created by jason on 15/07/2016.
 */
public class SingletonFlaskSelector<Item> implements FlaskSelector<Item> {
	final Flask<?> flask;

	public SingletonFlaskSelector(Flask<?> flask) {
		this.flask = flask;
	}

	@Override
	public Flask<?> getItemFlask(Item item) {
		return flask;
	}
}
