package com.airta.action.agent.action.atom;

import com.airta.action.agent.action.raw.RawAction;
import com.airta.action.agent.message.ActionResultProducer;
import com.airta.action.agent.message.ResultProducer;

public interface IAction {

    void exec(String key, RawAction rawAction);

    void report(String key, RawAction rawAction, ResultProducer resultProducer);

    void interval();

}
