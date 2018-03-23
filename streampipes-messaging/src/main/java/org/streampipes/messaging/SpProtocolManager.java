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

package org.streampipes.messaging;

import org.streampipes.model.grounding.TransportProtocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum SpProtocolManager {

  INSTANCE;

  private List<SpProtocolDefinitionFactory<? extends TransportProtocol>> availableProtocols;

  SpProtocolManager() {
    this.availableProtocols = new ArrayList<>();
  }

  public void register(SpProtocolDefinitionFactory<? extends TransportProtocol> protocolDefinition) {
    availableProtocols.add(protocolDefinition);
  }

  public List<SpProtocolDefinitionFactory<? extends TransportProtocol>> getAvailableProtocols() {
    return availableProtocols;
  }

  public <T extends TransportProtocol> Optional<SpProtocolDefinition<T>> findDefinition(T
                                                                         transportProtocol) {
    // TODO add RDF URI for protocol in model
    return this.availableProtocols
            .stream()
            .filter
                    (adf -> adf.getTransportProtocolClass().equals(transportProtocol.getClass()
                            .getCanonicalName()))
            .map(s -> (SpProtocolDefinitionFactory<T>) s)
            .map(SpProtocolDefinitionFactory::createInstance)
            .findFirst();

  }
}
