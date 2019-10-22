/*
 *  Copyright 2019 ADVA Optical Networking SE. All rights reserved.
 *
 *  Owner: mateuszma
 *
 *  : $
 */
package com.dev.life.rsocket.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GreetingsRequest {
  private String name;
}
