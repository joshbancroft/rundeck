/*
 * Copyright 2010 DTO Labs, Inc. (http://dtolabs.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/*
* ExecutionServiceMgr.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: Mar 3, 2010 11:06:47 AM
* $Id$
*/
package com.dtolabs.rundeck.core.execution;

import com.dtolabs.rundeck.core.common.Framework;
import com.dtolabs.rundeck.core.dispatcher.IDispatchedScript;

import java.util.*;

/**
 * ExecutionServiceFactory creates ExecutionServices.
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 * @version $Revision$
 */
public class ExecutionServiceFactory {
    final static HashMap<Class<? extends ExecutionItem>, Class<? extends Executor>> defaultExecutorClasses =
        new HashMap<Class<? extends ExecutionItem>, Class<? extends Executor>>();
    final static HashMap<Class<? extends ExecutionItem>, Executor> defaultExecutors =
        new HashMap<Class<? extends ExecutionItem>, Executor>();

    /**
     * Reset default executor classes
     */
    public static void resetDefaultExecutorClasses() {
        //register default executors
        defaultExecutorClasses.put(DispatchedScriptExecutionItem.class, DispatchedScriptExecutor.class);
    }

    static {
        resetDefaultExecutorClasses();
    }

    /**
     * Constructor uses default executor classes to create registration.
     */
    private ExecutionServiceFactory() {
    }


    /**
     * Singleton instance of the factory
     */
    private static final ExecutionServiceFactory instance = new ExecutionServiceFactory();


    /**
     * Return the factory instance
     *
     * @return instance
     */
    public static ExecutionServiceFactory instance() {
        return instance;
    }

    /**
     * Set a default Executor subclass for an ExecutionItem subclass
     *
     * @param execItemClass class
     * @param executorClass class
     */
    public static void setDefaultExecutorClass(final Class<? extends ExecutionItem> execItemClass,
                                               final Class<? extends Executor> executorClass) {
        defaultExecutorClasses.put(execItemClass, executorClass);
    }


    /**
     * Set a default Executor instance for an ExecutionItem subclass
     *
     * @param execItemClass class
     * @param executor instance
     */
    public static void setDefaultExecutor(final Class<? extends ExecutionItem> execItemClass,
                                               final Executor executor) {
        defaultExecutors.put(execItemClass, executor);
    }


    /**
     * Create an ExecutionService implementation
     *
     * @param framework framework
     *
     * @return ExecutionService
     */
    public ExecutionService createExecutionService(final Framework framework) {
        return new ExecutionServiceImpl(defaultExecutorClasses, defaultExecutors, framework);
    }

    /**
     * Create an ExecutionService implementation with a Listener
     *
     * @param framework framework
     * @param listener  listener
     *
     * @return ExecutionService
     */
    public ExecutionService createExecutionService(final Framework framework, final ExecutionListener listener) {
        final ExecutionServiceImpl service = new ExecutionServiceImpl(defaultExecutorClasses, defaultExecutors, framework);
        service.setListener(listener);
        return service;
    }


    /**
     * Construct a DispatchedScriptExecutionItem
     *
     * @param dispatchedScript dispatched script
     *
     * @return item
     */
    public static DispatchedScriptExecutionItem createDispatchedScriptExecutionItem(
        final IDispatchedScript dispatchedScript) {
        return new DispatchedScriptExecutionItemImpl(dispatchedScript);

    }
}
