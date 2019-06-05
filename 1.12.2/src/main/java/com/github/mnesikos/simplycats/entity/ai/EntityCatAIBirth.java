package com.github.mnesikos.simplycats.entity.ai;

import com.github.mnesikos.simplycats.entity.EntityCat;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

public class EntityCatAIBirth extends EntityAIBase {

    private final EntityCat MOTHER;
    private EntityCat FATHER;
    World WORLD;
    //int KITTEN_DELAY; // might include later, thinking one kitten spawns at a time

    public EntityCatAIBirth(EntityCat entityCat) {
        this.MOTHER = entityCat;
        this.WORLD = entityCat.world;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if (this.MOTHER.getSex() == 1 || !this.MOTHER.getBreedingStatus("ispregnant") || this.MOTHER.getBreedingStatus("inheat"))
            return false;

        else if (this.MOTHER.getMateTimer() >= 0)
            return false;

        else
            return true;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.MOTHER.getBreedingStatus("ispregnant");
    }

    @Override
    public void resetTask() {
        this.FATHER = null;
        //KITTEN_DELAY = 0;
    }

    @Override
    public void updateTask() {
        /*++KITTEN_DELAY;
        if (KITTEN_DELAY >= 60)*/

        for (int i = 0; i < this.MOTHER.getKittens("total"); i++) {
            this.FATHER = new EntityCat(this.WORLD);
            FATHER.readFromNBT(this.MOTHER.getFather(i));
            this.MOTHER.setFather(i, null, "");

            this.spawnBaby(this.FATHER, this.MOTHER.getFatherName(i));
        }

        this.MOTHER.setKittens(0);
        this.MOTHER.setBreedingStatus("ispregnant", false);
        this.MOTHER.setTimeCycle("end", 24000 * 16); // sets out of heat for 16 minecraft days after birth
    }

    private void spawnBaby(EntityCat father, String fatherName) {
        EntityCat child = this.MOTHER.createChild(father);

        final net.minecraftforge.event.entity.living.BabyEntitySpawnEvent event = new net.minecraftforge.event.entity.living.BabyEntitySpawnEvent(MOTHER, father, child);
        child = (EntityCat) event.getChild();

        if (child != null) {
            child.setGrowingAge(-24000);
            child.setLocationAndAngles(this.MOTHER.posX, this.MOTHER.posY, this.MOTHER.posZ, 0.0F, 0.0F);
            child.setParent("father", fatherName);
            child.setParent("mother", this.MOTHER.getCustomNameTag());
            this.WORLD.spawnEntity(child);

            Random random = this.MOTHER.getRNG();
            for (int i = 0; i < 7; ++i) {
                double d0 = random.nextGaussian() * 0.02D;
                double d1 = random.nextGaussian() * 0.02D;
                double d2 = random.nextGaussian() * 0.02D;
                this.WORLD.spawnParticle(EnumParticleTypes.HEART, this.MOTHER.posX + (double) (random.nextFloat() * this.MOTHER.width * 2.0F) - (double) this.MOTHER.width, this.MOTHER.posY + 0.5D + (double) (random.nextFloat() * this.MOTHER.height), this.MOTHER.posZ + (double) (random.nextFloat() * this.MOTHER.width * 2.0F) - (double) this.MOTHER.width, d0, d1, d2);
            }

            if (this.WORLD.getGameRules().getBoolean("doMobLoot")) {
                this.WORLD.spawnEntity(new EntityXPOrb(this.WORLD, this.MOTHER.posX, this.MOTHER.posY, this.MOTHER.posZ, random.nextInt(2) + 1));
            }
        }
    }
}