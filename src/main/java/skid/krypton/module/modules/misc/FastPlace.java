@EventListener
public void onPostItemUse(final PostItemUseEvent postItemUseEvent) {
    final ItemStack getMainHandStack = this.mc.player.getMainHandStack();
    final ItemStack getItemUseTime = this.mc.player.getOffHandStack();
    final Item item = getMainHandStack.getItem();
    final Item item2 = getItemUseTime.getItem();

    if (!getMainHandStack.isOf(Items.EXPERIENCE_BOTTLE) && !getItemUseTime.isOf(Items.EXPERIENCE_BOTTLE) && this.onlyXP.getValue()) {
        return;
    }
    if (!this.onlyXP.getValue()) {
        if (item instanceof BlockItem || item2 instanceof BlockItem) {
            if (!this.allowBlocks.getValue()) {
                return;
            }
        } else if (!this.allowItems.getValue()) {
            return;
        }
    }

    if (item.getFoodComponent() != null) {
        return;
    }
    if (item2.getFoodComponent() != null) {
        return;
    }

    if (getMainHandStack.isOf(Items.RESPAWN_ANCHOR) || getMainHandStack.isOf(Items.GLOWSTONE) ||
        getItemUseTime.isOf(Items.RESPAWN_ANCHOR) || getItemUseTime.isOf(Items.GLOWSTONE)) {
        return;
    }
    if (item instanceof RangedWeaponItem || item2 instanceof RangedWeaponItem) {
        return;
    }
    postItemUseEvent.cooldown = this.useDelay.getIntValue();
        }
