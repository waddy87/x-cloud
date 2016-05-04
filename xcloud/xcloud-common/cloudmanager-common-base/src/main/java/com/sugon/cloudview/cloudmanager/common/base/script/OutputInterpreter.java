package com.sugon.cloudview.cloudmanager.common.base.script;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;

public abstract class OutputInterpreter {
    public boolean drain() {
        return false;
    }

    public String processError(BufferedReader reader) throws IOException {
        StringBuilder buff = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            buff.append(line);
        }
        return buff.toString();
    }

    public abstract String interpret(BufferedReader reader) throws IOException;

    public static final OutputInterpreter NoOutputParser = new OutputInterpreter() {
        @Override
        public String interpret(BufferedReader reader) throws IOException {
            return null;
        }
    };

    public static class TimedOutLogger extends OutputInterpreter {
        Process _process;

        public TimedOutLogger(Process process) {
            _process = process;
        }

        @Override
        public boolean drain() {
            return true;
        }

        @Override
        public String interpret(BufferedReader reader) throws IOException {
            StringBuilder buff = new StringBuilder();

            while (reader.ready()) {
                buff.append(reader.readLine());
            }

            _process.destroy();

            try {
                while (reader.ready()) {
                    buff.append(reader.readLine());
                }
            } catch (IOException e) {
            }

            return buff.toString();
        }
    }

    public static class OutputLogger extends OutputInterpreter {
        Logger _logger;

        public OutputLogger(Logger logger) {
            _logger = logger;
        }

        @Override
        public String interpret(BufferedReader reader) throws IOException {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            if (builder.length() > 0) {
                _logger.debug(builder.toString());
            }
            return null;
        }
    }

    public static class OneLineParser extends OutputInterpreter {
        String line = null;

        @Override
        public String interpret(BufferedReader reader) throws IOException {
            line = reader.readLine();
            return null;
        }

        public String getLine() {
            return line;
        }
    };

    public static class AllLinesParser extends OutputInterpreter {
        String allLines = null;

        @Override
        public String interpret(BufferedReader reader) throws IOException {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            allLines = builder.toString();
            return null;
        }

        public String getLines() {
            return allLines;
        }
    }

}
