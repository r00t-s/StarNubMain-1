/*
 * Copyright (C) 2014 www.StarNub.org - Underbalanced
 *
 * This file is part of org.starnub a Java Wrapper for Starbound.
 *
 * This above mentioned StarNub software is free software:
 * you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free
 * Software Foundation, either version  3 of the License, or
 * any later version. This above mentioned CodeHome software
 * is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 * the GNU General Public License for more details. You should
 * have received a copy of the GNU General Public License in
 * this StarNub Software.  If not, see <http://www.gnu.org/licenses/>.
 */

package starnubserver.plugins.resources;

import starnubserver.resources.StarNubYamlWrapper;

import java.io.InputStream;

/**
 * Represents StarNubs Configuration instance extending YAMLWrapper
 *
 * @author Daniel (Underbalanced) (www.StarNub.org)
 * @since 1.0 Beta
 */
public class PluginConfiguration extends StarNubYamlWrapper {

    public PluginConfiguration(String pluginName, InputStream defaultPath) {
        super(
                pluginName,
                pluginName.toLowerCase() + "_configuration.yml",
                defaultPath,
                "StarNub/Plugins/" + pluginName,
                false,
                true,
                true,
                true,
                true
        );
    }
}
