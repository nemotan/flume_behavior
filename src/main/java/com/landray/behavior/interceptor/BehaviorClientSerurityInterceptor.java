/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.landray.behavior.interceptor;

import java.util.List;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.HostInterceptor;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.behavior.security.BehaviorSecurityClient;

/**
 * Interceptor class that appends a static, pre-configured header to all events.
 * 
 * Properties:
 * <p>
 * 
 * key: Key to use in static header insertion. (default is "key")
 * <p>
 * 
 * value: Value to use in static header insertion. (default is "value")
 * <p>
 * 
 * preserveExisting: Whether to preserve an existing value for 'key' (default is
 * true)
 * <p>
 * 
 * Sample config:
 * <p>
 * 
 * <code>
 *   agent.sources.r1.channels = c1<p>
 *   agent.sources.r1.type = SEQ<p>
 *   agent.sources.r1.interceptors = i1<p>
 *   agent.sources.r1.interceptors.i1.type = static<p>
 *   agent.sources.r1.interceptors.i1.preserveExisting = false<p>
 *   agent.sources.r1.interceptors.i1.key = datacenter<p>
 *   agent.sources.r1.interceptors.i1.value= NYC_01<p>
 * </code>
 * 
 */
public class BehaviorClientSerurityInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(BehaviorClientSerurityInterceptor.class);

	/**
	 * Only {@link HostInterceptor.Builder} can build me
	 */
	private BehaviorClientSerurityInterceptor() {
	}

	public void initialize() {
		// no-op
	}

	/**
	 * Modifies events in-place.
	 */
	public Event intercept(Event event) {
		String key = new String(event.getHeaders().get("key"));
		try {
			byte[] securityBody = BehaviorSecurityClient.encode(
					event.getBody(), key);
			logger.debug("加密之后的内容为:" + new String(securityBody));
			event.setBody(securityBody);
		} catch (Exception e) {
			logger.error("event body 加密出错", e);
		}
		return event;
	}

	/**
	 * Delegates to {@link #intercept(Event)} in a loop.
	 * 
	 * @param events
	 * @return
	 */
	public List<Event> intercept(List<Event> events) {
		for (Event event : events) {
			intercept(event);
		}
		return events;
	}

	public void close() {
		// no-op
	}

	/**
	 * Builder which builds new instance of the StaticInterceptor.
	 */
	public static class Builder implements Interceptor.Builder {
		public void configure(Context context) {
		}

		public Interceptor build() {
			return new BehaviorClientSerurityInterceptor();
		}

	}
}
