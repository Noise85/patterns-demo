package com.patterns.abstractfactory.isolation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 1: UI Theme System
 * <p>
 * These tests validate the Abstract Factory pattern implementation
 * for creating themed UI component families.
 */
class IsolationExerciseTest {

    @Test
    void lightFactory_createsLightButton() {
        UIFactory factory = new LightThemeFactory();
        Button button = factory.createButton("Submit");

        String rendered = button.render();

        assertThat(rendered)
                .contains("[Light]")
                .contains("Button")
                .contains("Submit");
    }

    @Test
    void lightFactory_createsLightCheckbox() {
        UIFactory factory = new LightThemeFactory();
        Checkbox checkbox = factory.createCheckbox("Accept Terms");

        String rendered = checkbox.render();

        assertThat(rendered)
                .contains("[Light]")
                .contains("Checkbox")
                .contains("Accept Terms");
    }

    @Test
    void darkFactory_createsDarkButton() {
        UIFactory factory = new DarkThemeFactory();
        Button button = factory.createButton("Cancel");

        String rendered = button.render();

        assertThat(rendered)
                .contains("[Dark]")
                .contains("Button")
                .contains("Cancel");
    }

    @Test
    void darkFactory_createsDarkCheckbox() {
        UIFactory factory = new DarkThemeFactory();
        Checkbox checkbox = factory.createCheckbox("Remember Me");

        String rendered = checkbox.render();

        assertThat(rendered)
                .contains("[Dark]")
                .contains("Checkbox")
                .contains("Remember Me");
    }

    @Test
    void factoriesCreateConsistentFamily_lightTheme() {
        UIFactory factory = new LightThemeFactory();
        Button button = factory.createButton("OK");
        Checkbox checkbox = factory.createCheckbox("Enable");

        assertThat(button.render()).startsWith("[Light]");
        assertThat(checkbox.render()).startsWith("[Light]");
    }

    @Test
    void factoriesCreateConsistentFamily_darkTheme() {
        UIFactory factory = new DarkThemeFactory();
        Button button = factory.createButton("Save");
        Checkbox checkbox = factory.createCheckbox("Notify");

        assertThat(button.render()).startsWith("[Dark]");
        assertThat(checkbox.render()).startsWith("[Dark]");
    }

    @Test
    void clientCode_worksWithAnyFactory() {
        // Simulate client code that works with factory interface
        UIFactory[] factories = {new LightThemeFactory(), new DarkThemeFactory()};

        for (UIFactory factory : factories) {
            Button button = factory.createButton("Test");
            Checkbox checkbox = factory.createCheckbox("TestBox");

            // Client can use products without knowing concrete types
            assertThat(button.render()).contains("Button");
            assertThat(checkbox.render()).contains("Checkbox");
        }
    }
}
