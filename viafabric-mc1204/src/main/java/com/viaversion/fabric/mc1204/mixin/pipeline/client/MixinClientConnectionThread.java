/*
 * This file is part of ViaFabric - https://github.com/ViaVersion/ViaFabric
 * Copyright (C) 2018-2024 ViaVersion and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.viaversion.fabric.mc1204.mixin.pipeline.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.viaversion.fabric.mc1204.ViaFabric;
import com.viaversion.fabric.mc1204.service.ProtocolAutoDetector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Mixin(targets = "net/minecraft/client/gui/screen/multiplayer/ConnectScreen$1")
public class MixinClientConnectionThread {
    @Inject(method = "run", at = @At(value = "INVOKE_ASSIGN", args = "fuzz=2", target = "Ljava/util/Optional;get()Ljava/lang/Object;"))
    private void onConnect(CallbackInfo ci, @Local InetSocketAddress address) {
        try {
            if (!ViaFabric.config.isClientSideEnabled()) return;
            ProtocolAutoDetector.detectVersion(address).get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            ViaFabric.JLOGGER.log(Level.WARNING, "Could not auto-detect protocol for " + address + " " + e);
        }
    }
}