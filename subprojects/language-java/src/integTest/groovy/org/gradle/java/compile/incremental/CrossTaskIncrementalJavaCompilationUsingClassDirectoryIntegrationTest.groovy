/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.gradle.java.compile.incremental

class CrossTaskIncrementalJavaCompilationUsingClassDirectoryIntegrationTest extends AbstractCrossTaskIncrementalJavaCompilationIntegrationTest {

    @Override
    protected String getProjectDependencyBlock() {
        '''
            subprojects {
                configurations {
                    classesDir {
                        extendsFrom(compile)
                    }
                }
                artifacts {
                    classesDir file: compileJava.destinationDir, builtBy:compileJava
                }
            }
            project(':impl') {
                dependencies { compile project(path:':api', configuration: 'classesDir') }
            }
        '''
    }

    @Override
    protected void addDependency(String from, String to) {
        buildFile << """
            project(':$from') {
                dependencies { compile project(path:':$to', configuration: 'classesDir') }
            }
        """
    }
}
