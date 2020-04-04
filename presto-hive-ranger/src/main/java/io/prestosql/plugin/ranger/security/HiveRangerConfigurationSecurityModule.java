/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.plugin.ranger.security;

import com.google.inject.Binder;
import com.google.inject.Module;
import io.airlift.configuration.AbstractConfigurationAwareModule;
import io.prestosql.plugin.hive.security.SecurityConfig;

import static io.airlift.configuration.ConditionalModule.installModuleIf;

public class HiveRangerConfigurationSecurityModule
        extends AbstractConfigurationAwareModule
{
    @Override
    protected void setup(Binder binder)
    {
        bindSecurityModule("ranger", new HiveRangerSecurityModule());
    }

    private void bindSecurityModule(String name, Module module)
    {
        install(installModuleIf(
                SecurityConfig.class,
                security -> name.equalsIgnoreCase(security.getSecuritySystem()),
                module));
    }
}
