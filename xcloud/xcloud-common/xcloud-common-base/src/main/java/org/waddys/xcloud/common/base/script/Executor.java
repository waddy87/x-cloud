package org.waddys.xcloud.common.base.script;

import org.slf4j.Logger;

/**
 * Executor figures out how to execute a certain script.
 */
public interface Executor {
    String execute(Script script, long timeout, Logger logger);
}
