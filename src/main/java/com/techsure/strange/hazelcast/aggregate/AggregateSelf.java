package com.techsure.strange.hazelcast.aggregate;

import com.hazelcast.jet.aggregate.AggregateOperation;
import com.hazelcast.jet.aggregate.AggregateOperation1;
import com.hazelcast.jet.datamodel.Tag;
import com.hazelcast.jet.function.DistributedBiConsumer;
import com.hazelcast.jet.function.DistributedFunction;
import com.hazelcast.jet.function.DistributedSupplier;

/**
 * @author zhoujian
 * @Date 2018/7/17 12:12
 */
public class AggregateSelf implements AggregateOperation {
	@Override
	public int arity() {
		return 0;
	}

	@Override
	public DistributedSupplier createFn() {
		return null;
	}

	@Override
	public DistributedBiConsumer accumulateFn(Tag tag) {
		return null;
	}

	@Override
	public DistributedBiConsumer combineFn() {
		return null;
	}

	@Override
	public DistributedBiConsumer deductFn() {
		return null;
	}

	@Override
	public DistributedFunction exportFn() {
		return null;
	}

	@Override
	public DistributedFunction finishFn() {
		return null;
	}

	@Override
	public AggregateOperation withAccumulateFns(DistributedBiConsumer[] accumulateFns) {
		return null;
	}

	@Override
	public AggregateOperation withIdentityFinish() {
		return null;
	}

	@Override
	public AggregateOperation andThen(DistributedFunction distributedFunction) {
		return null;
	}

	@Override
	public AggregateOperation1 withCombiningAccumulateFn(DistributedFunction getAccFn) {
		return null;
	}


	@Override
	public DistributedBiConsumer accumulateFn(int index) {
		return null;
	}
}
