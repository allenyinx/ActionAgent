package com.airta.action.agent.action.atom;

import com.airta.action.agent.action.raw.RawAction;

public interface IAction {

    void exec(String key, RawAction rawAction);

    void report(String key, RawAction rawAction);

    void interval();

}
