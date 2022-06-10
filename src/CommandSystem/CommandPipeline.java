package CommandSystem;

import CommandSystem.Enum.Commands;

import java.util.ArrayList;

public class CommandPipeline {
    private Commands command = null;
    private ArrayList<String> params;
//    private boolean isValid = false;
    int paramsCount = 0;

    public CommandPipeline() {
    }

    public CommandPipeline setCommand(Commands command){
        this.command = command;
        return this;
    }

    public CommandPipeline setParams(ArrayList<String> params){
        this.params = params;
        return this;
    }

    public Commands getCommand() {
        return command;
    }

    public String getNextParam() {
        return params == null ? null : this.params.get(paramsCount++);
    }

    public boolean isValid() {
        return this.command.getParamsCount() != (params == null ? 0 : params.size());
    }

    public boolean isValid(Commands variant) {
        return variant.getParamsCount() == (params == null ? 0 : params.size());
    }

    public int getParamsCount(){
        return this.params == null ? 0 : this.params.size();
    }

    @Override
    public String toString() {
        return "Services.CommandSystem.CommandPipeline{" +
                "command=" + command +
                ", params=" + params +
                ", paramsCount=" + paramsCount +
                '}';
    }
}
