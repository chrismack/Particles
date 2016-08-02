/**
 * 
 */
package com.chrismack.particles;

import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

/**
 * @author Chris Mack
 *
 */
@Plugin(id="particles", name="Particles", description="A particle testing plugin", authors="Chris Mack")
public class Particles
{
	private Task task;
	
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join event, @First Player player)
	{
		task = Sponge.getScheduler().createAsyncExecutor(this).scheduleWithFixedDelay(new Runnable()
		{
			int count = 0;
			@Override
			public void run()
			{
				count++;
				
				halo(player);	// Switch for different effects
				
				if(count > 30)
					task.cancel();
			}
		}, 0, 125, TimeUnit.MILLISECONDS).getTask();
		
	}
	
	private void halo(Player player)
	{
		World world = player.getWorld();
		
		double rad = 2.5;
		
		for(int i = 0; i <= 50; i ++)
		{
			double x = rad * Math.cos(i);
			double z = rad * Math.sin(i);
			
			Vector3d pos = new Vector3d(player.getLocation().getPosition()).add(x, 1, z);
			
			world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.REDSTONE).build(), pos);
		}
	}
	
	private void spiral(Player player)
	{
		
		World world = player.getWorld();
		double rad = 5;
		
		for(double y = 5; y >= 0; y -= 0.07)		// Starting height and frequency of paricles 
		{
			rad = y / 5;							//Taper towards top
			double x = rad * Math.cos(5 * y);		//Same as halo times by the number of rotation
			double z = rad * Math.sin(5 * y);
			double y2 = 5 - y;						
			
			Vector3d pos = new Vector3d(player.getLocation().getPosition()).add(x, y2, z);
			world.spawnParticles(ParticleEffect.builder().type(ParticleTypes.REDSTONE).build(), pos);
		}
	}
	
}
