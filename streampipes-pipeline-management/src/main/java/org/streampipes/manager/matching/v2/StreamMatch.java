/*
 * Copyright 2018 FZI Forschungszentrum Informatik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.streampipes.manager.matching.v2;

import java.util.List;

import org.streampipes.manager.matching.v2.utils.MatchingUtils;
import org.streampipes.model.client.matching.MatchingResultMessage;
import org.streampipes.model.client.matching.MatchingResultType;
import org.streampipes.model.grounding.EventGrounding;
import org.streampipes.model.schema.EventSchema;
import org.streampipes.model.SpDataStream;
import org.streampipes.model.quality.EventStreamQualityDefinition;
import org.streampipes.model.quality.EventStreamQualityRequirement;

public class StreamMatch extends AbstractMatcher<SpDataStream, SpDataStream> {

	public StreamMatch() {
		super(MatchingResultType.STREAM_MATCH);
	}

	@Override
	public boolean match(SpDataStream offer, SpDataStream requirement, List<MatchingResultMessage> errorLog) {
		return MatchingUtils.nullCheck(offer, requirement) ||
				(checkSchemaMatch(offer.getEventSchema(), requirement.getEventSchema(), errorLog) &&
				 checkGroundingMatch(offer.getEventGrounding(), requirement.getEventGrounding(), errorLog) &&
				 checkStreamQualityMatch(offer.getHasEventStreamQualities(), requirement.getRequiresEventStreamQualities(), errorLog));
	}
	
	public boolean matchIgnoreGrounding(SpDataStream offer, SpDataStream requirement, List<MatchingResultMessage> errorLog) {
		boolean match = /*MatchingUtils.nullCheckReqAllowed(offer, requirement) ||*/
				(checkSchemaMatch(offer.getEventSchema(), requirement.getEventSchema(), errorLog) &&
				 checkStreamQualityMatch(offer.getHasEventStreamQualities(), requirement.getRequiresEventStreamQualities(), errorLog));
		return match;
	}

	private boolean checkGroundingMatch(EventGrounding offer,
			EventGrounding requirement, List<MatchingResultMessage> errorLog) {
		return new GroundingMatch().match(offer, requirement, errorLog);
	}

	private boolean checkStreamQualityMatch(
			List<EventStreamQualityDefinition> offer,
			List<EventStreamQualityRequirement> requirement, List<MatchingResultMessage> errorLog) {
		boolean match = MatchingUtils.nullCheck(offer, requirement) || requirement
				.stream()
				.allMatch(req -> offer
						.stream()
						.anyMatch(of -> new StreamQualityMatch().match(of, req, errorLog)));
		
		if (!match) buildErrorMessage(errorLog, MatchingResultType.STREAM_QUALITY, "quality");
		return match;
	}

	private boolean checkSchemaMatch(EventSchema offer,
			EventSchema requirement, List<MatchingResultMessage> errorLog) {
		boolean match = new SchemaMatch().match(offer, requirement, errorLog);
		return match;
	}

}
