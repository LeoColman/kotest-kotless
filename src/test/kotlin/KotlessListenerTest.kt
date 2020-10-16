/*
 * Copyright 2020 Leonardo Colman Lopes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.colman.kotest.kotless

import com.github.kittinunf.fuel.httpGet
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.net.ServerSocket

class KotlessListenerTest : FunSpec({
    
    listeners(KotlessListener(9999))
    
    test("Should load the application") {
        "http://localhost:9999/foo".httpGet().responseString().third.get() shouldBe """{"foo":"bar"}"""
    }
    
    afterSpec { 
        // Should have cleared the port usage
        @Suppress("BlockingMethodInNonBlockingContext")
        ServerSocket(9999)
    }
})

