package org.waddys.xcloud.common.base.script;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

public class ScriptBuilder {
    Logger _logger;
    long _timeout;
    String _command;
    ArrayList<String> _params;
    Executor _executor;

    public ScriptBuilder(String command, Executor executor, long timeout, Logger logger) {
        _command = command;
        _timeout = timeout;
        _logger = logger;
        _executor = executor;

    }

    public ScriptBuilder add(String... params) {
        for (String param : params) {
            _params.add(param);
        }

        return this;
    }

    public Script script() {
        return new Script(this);
    }

    public List<String> getParameterNames() {
        return _params;
    }

    public String getCommand() {
        return _command;
    }

    public long getTimeout() {
        return _timeout;
    }

    public Logger getLogger() {
        return _logger;
    }

    public Executor getExecutor() {
        return _executor;
    }
}
