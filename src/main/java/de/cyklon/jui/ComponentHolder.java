package de.cyklon.jui;

import de.cyklon.jui.component.UIComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;

public interface ComponentHolder {


	public void addComponents(@NotNull UIComponent... components);

	public void clearComponents();

	public @Unmodifiable Collection<UIComponent> getComponents();

	public @Nullable UIComponent getComponent(long id);

	public void removeComponent(long id);

}
