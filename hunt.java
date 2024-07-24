String username = "";
String password = "";
int boxTrapId = 10008;
WorldPoint wp1 = new WorldPoint(3139, 3785, 0);
WorldPoint wp2 = new WorldPoint(3138, 3784, 0);
WorldPoint wp3 = new WorldPoint(3139, 3784, 0);
WorldPoint wp4 = new WorldPoint(3138, 3785, 0);
WorldPoint wp5 = new WorldPoint(3136, 3782, 0);
WorldPoint wp6 = new WorldPoint(3141, 3782, 0);
//WorldPoint wp1 = new WorldPoint(3140,3773, 0);
//WorldPoint wp2 = new WorldPoint(wp1.getX()-1,wp1.getY()+1, 0);
//WorldPoint wp3 = new WorldPoint(wp1.getX()-1,wp1.getY()-1, 0);
//WorldPoint wp4 = new WorldPoint(wp1.getX()+1,wp1.getY()+1, 0);
//WorldPoint wp5 = new WorldPoint(wp1.getX()+1,wp1.getY()-1, 0);
//WorldPoint wp6 = new WorldPoint(wp1.getX()+2,wp1.getY(), 0);
net.runelite.api.coords.WorldPoint exitTeleport = new WorldPoint(3146, 3753, 0);
if (client.getGameState().equals(GameState.LOGGED_IN)) {
	v.getVars().setInt("LOGOUT_TIMER", 0);
	if (v.getInventory().amountInInventory(11959, 50, 1000)) {
		if (v.getWalking().nearEntity(Entity.GAME, 10355, 25)) {
			if(v.getEquipment().hasEquipped(1704)) {
				v.getEquipment().unequip(KitType.AMULET);
			} else if(v.getEquipment().hasEquipped(2572)) {
				v.getEquipment().unequipRing(2572);
			} else {
				v.getBank().depositAll();
			}
			
		} else {
			if (v.getWalking().nearPosition(exitTeleport, 3)) {
				//TELEPORT
				if(!v.getVars().getBoolean("TELEPORTING")) {
					v.getEquipment().selectOption(EquipmentInventorySlot.AMULET, "Edgeville", 2);
					v.getVars().setBoolean("TELEPORTING", true);
					v.getCallbacks().afterTicks(5, ()-> {
						v.getVars().setBoolean("TELEPORTING", false);
					});
				}

			} else {
				if (!v.getWalking().isMoving()) {
					v.getWalking().walkR(exitTeleport,3);
				}
			}
		}
	} else if (v.getWalking().nearPosition(wp1, 30)) {
		Player playerTarget = v.getCombat().playerTarget();
		//LOOP THROUHG ALL THE PLAYERS AND SEE IF THEY ARE IN A DISTANCE OF 25 TILES
		if (playerTarget != null) {
			if(client.getBoostedSkillLevel(Skill.HITPOINTS) < 65) {
				if(!v.getCombat().isFrozen()) {
					if (v.getWalking().nearPosition(exitTeleport, 3)) {
						//TELEPORT
						v.getEquipment().selectOption(EquipmentInventorySlot.AMULET, "Edgeville", 2);
					} else {
						if (!v.getWalking().isMoving()) {
							v.getPrayer().prayOn(Prayer.PROTECT_FROM_MAGIC);
							v.getWalking().walkR(exitTeleport,3);
						}
					}
				} else {
					v.getPrayer().prayOn(Prayer.PROTECT_FROM_MAGIC);
				}
			} else {
				v.getGame().logout();
			}
		} else if (client.getPlayers().stream().anyMatch(player -> !player.equals(client.getLocalPlayer()) && v.getPlayer().canPvpMe(player) && (player.getWorldLocation().distanceTo(client.getLocalPlayer().getWorldLocation()) < 30 && (player.getSkullIcon() != null || player.getOverheadIcon() != null)) || !player.equals(client.getLocalPlayer()) && v.getPlayer().canPvpMe(player) && (player.getWorldLocation().distanceTo(client.getLocalPlayer().getWorldLocation()) < 15 &&  player.getPlayerComposition() != null && player.getPlayerComposition().getEquipmentId(KitType.WEAPON) != -1))) {
			v.getGame().logout();
		} else if (v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving()) {
//			if(!v.getInventory().amountInInventory(boxTrapId, 1, 28)) {
//				GameObject caughtBox = v.getGameObject().findNearestByDistance(5, 721);
//				GameObject resetBox = v.getGameObject().findNearestByDistance(5, 9385);
//				int caughtCount = v.getGameObject().countByDistance(5, 721);
//				int resetCount = v.getGameObject().countByDistance(5, 9385);
//				int layCount = v.getGameObject().countByDistance(5, 9380);
//				int totalCount = caughtCount + resetCount + layCount;
//				if(totalCount == 0) {
//					if (v.getWalking().nearPosition(exitTeleport, 3)) {
//						//TELEPORT
//						v.getEquipment().selectOption(EquipmentInventorySlot.AMULET, "Edgeville", 2);
//					} else {
//						if (!v.getWalking().isMoving()) {
//							v.getWalking().walkR(exitTeleport,3);
//						}
//					}
//				} else {
//					if(!v.getVars().getBoolean("RESETTING")) {
//						if (caughtBox != null) {
//							v.getGameObject().invokeByDistance("Reset", "<col=ffff>Shaking box", 721, 4, 8);
//							v.getVars().setBoolean("RESETTING", true);
//							v.getCallbacks().afterTicks(2, () -> {
//								v.getVars().setBoolean("RESETTING", false);
//							});
//						} else if (resetBox != null) {
//							v.getGameObject().invokeByDistance("Reset", "<col=ffff>Box trap", 9385, 4, 8);
//							v.getVars().setBoolean("RESETTING", true);
//							v.getCallbacks().afterTicks(2, () -> {
//								v.getVars().setBoolean("RESETTING", false);
//							});
//						} else {
//							v.getGroundItem().take(10008);
//						}
//					}
//				}
//
//			} else 
			if(!v.getVars().getBoolean("RESETTING")) {
				GameObject caughtBox = v.getGameObject().findNearestByDistance(5, 721);
				GameObject resetBox = v.getGameObject().findNearestByDistance(5, 9385);
				int caughtCount = v.getGameObject().countByDistance(5, 721);
				int resetCount = v.getGameObject().countByDistance(5, 9385);
				int layCount = v.getGameObject().countByDistance(5, 9380);
				int totalCount = caughtCount + resetCount + layCount;

				boolean item1Queue = v.getGroundItem().tileContainsItem(wp1, boxTrapId);
				boolean item2Queue = v.getGroundItem().tileContainsItem(wp2, boxTrapId);
				boolean item3Queue = v.getGroundItem().tileContainsItem(wp3, boxTrapId);
				boolean item4Queue = v.getGroundItem().tileContainsItem(wp4, boxTrapId);
				boolean item5Queue = v.getGroundItem().tileContainsItem(wp5, boxTrapId);
				boolean item6Queue = v.getGroundItem().tileContainsItem(wp6, boxTrapId);

				boolean game1Queue = v.getWalking().tileContainsEntity(Entity.GAME, wp1);
				boolean game2Queue = v.getWalking().tileContainsEntity(Entity.GAME, wp2);
				boolean game3Queue = v.getWalking().tileContainsEntity(Entity.GAME, wp3);
				boolean game4Queue = v.getWalking().tileContainsEntity(Entity.GAME, wp4);
				boolean game5Queue = v.getWalking().tileContainsEntity(Entity.GAME, wp5);
				boolean game6Queue = v.getWalking().tileContainsEntity(Entity.GAME, wp6);

				int layOpCode = 9764864;

				int limit = 7;
				WorldPoint currentLocation = client.getLocalPlayer().getWorldLocation();
				if (caughtBox != null) {
					v.getGameObject().invokeByDistance("Reset", "<col=ffff>Shaking box", 721, 4, 8);
					v.getVars().setBoolean("RESETTING", true);
					v.getCallbacks().afterTicks(2, () -> {
						v.getVars().setBoolean("RESETTING", false);
					});
				} else if (resetBox != null) {
					v.getGameObject().invokeByDistance("Reset", "<col=ffff>Box trap", 9385, 4, 8);
					v.getVars().setBoolean("RESETTING", true);
					v.getCallbacks().afterTicks(2, () -> {
						v.getVars().setBoolean("RESETTING", false);
					});
				} else if (totalCount < limit) {
					if (item1Queue && !game1Queue) {
						v.getGroundItem().take(wp1, 10008);
					} else if (item2Queue && !game2Queue) {
						v.getGroundItem().take(wp2, 10008);
					} else if (item3Queue && !game3Queue) {
						v.getGroundItem().take(wp3, 10008);
					} else if (item4Queue && !game4Queue) {
						v.getGroundItem().take(wp4, 10008);
					} else if (item5Queue && !game5Queue) {
						v.getGroundItem().take(wp5, 10008);
					} else if (item6Queue && !game6Queue) {
						v.getGroundItem().take(wp6, 10008);
					} else if(v.getInventory().amountInInventory(boxTrapId, 1, 28)) {
						if (!game1Queue) {
							if (!currentLocation.equals(wp1)) {
								v.getWalking().walk(wp1);
							} else {
								int slot = v.getInventory().slot(boxTrapId);
								if (slot != -1) {
									v.exec("LAY", 5, () -> {
										v.invoke("Lay", "<col=ff9040>Box trap</col>", 2, 57, slot, layOpCode, false);
									});
									
								}
							}
						} else if (!game2Queue) {
							if (!currentLocation.equals(wp2)) {
								v.getWalking().walk(wp2);
							} else {
								int slot = v.getInventory().slot(boxTrapId);
								if (slot != -1) {
									v.exec("LAY", 5, () -> {
										v.invoke("Lay", "<col=ff9040>Box trap</col>", 2, 57, slot, layOpCode, false);
									});
								}
							}
						} else if (!game3Queue) {
							if (!currentLocation.equals(wp3)) {
								v.getWalking().walk(wp3);
							} else {
								int slot = v.getInventory().slot(boxTrapId);
								if (slot != -1) {
									v.exec("LAY", 5, () -> {
										v.invoke("Lay", "<col=ff9040>Box trap</col>", 2, 57, slot, layOpCode, false);
									});
								}
							}
						} else if (!game4Queue) {
							if (!currentLocation.equals(wp4)) {
								v.getWalking().walk(wp4);
							} else {
								int slot = v.getInventory().slot(boxTrapId);
								if (slot != -1) {
									v.exec("LAY", 5, () -> {
										v.invoke("Lay", "<col=ff9040>Box trap</col>", 2, 57, slot, layOpCode, false);
									});
								}
							}
						} else if (!game5Queue) {
							if (!currentLocation.equals(wp5)) {
								v.getWalking().walk(wp5);
							} else {
								int slot = v.getInventory().slot(boxTrapId);
								if (slot != -1) {
									v.exec("LAY", 5, () -> {
										v.invoke("Lay", "<col=ff9040>Box trap</col>", 2, 57, slot, layOpCode, false);
									});
								}
							}
						} else if (!game6Queue) {
							if (!currentLocation.equals(wp6)) {
								v.getWalking().walk(wp6);
							} else {
								int slot = v.getInventory().slot(boxTrapId);
								if (slot != -1) {
									v.exec("LAY", 5, () -> {
										v.invoke("Lay", "<col=ff9040>Box trap</col>", 2, 57, slot, layOpCode, false);
									});
								}
							}
						}
					} else if ((item1Queue && game1Queue) || (item2Queue && game2Queue) || (item3Queue && game3Queue) || (item4Queue && game4Queue) || (item5Queue && game5Queue) || (item6Queue && game6Queue)) {
						v.getGroundItem().take(10008);
					}
				}
			}

		}
	} else if (v.getWalking().nearEntity(Entity.GAME, 12166, 10)) {
		log.info("WE ARE AT TREE");
		if (!v.getWalking().isMoving() && v.getLocalPlayer().hasAnimation(-1)) {
			log.info("WE ARE NOT MOVING GOING TO TREE");
			GameObject treeObj = v.getGameObject().findNearest(12166);
			if (treeObj != null) {
				int sceneX = treeObj.getSceneMinLocation().getX();
				int sceneY = treeObj.getSceneMinLocation().getY();
				Widget canoeChatDialogue = client.getWidget(229, 0);
				if (canoeChatDialogue != null && !canoeChatDialogue.isHidden()) {

				} else {
					Widget canoeTravelDialogue = client.getWidget(647, 1);
					if (canoeTravelDialogue != null && !canoeTravelDialogue.isHidden()) {
						v.invoke("Travel to<col=ff9040> Wilderness Pond", "", 1, 57, 0, 42401810, false);
					} else {
						  Widget canoeDialogue = client.getWidget(416, 0);
						  if (canoeDialogue != null && !canoeDialogue.isHidden()) {
							  v.invoke("Make<col=ff9040> Waka canoe", "", 1, 57, 0, 27262987, false);
						  } else {
							if (v.getWalking().nearEntity(Entity.GROUND, 7389, 15)) {
								ObjectComposition comp = client.getObjectDefinition(treeObj.getId());
								if(comp != null) {
									ObjectComposition imposterComp = comp.getImpostor();
									if(imposterComp != null) {
										if(imposterComp.getId() == 12150) {
											v.exec("FLOATCANOE", 10, () -> {
												v.invoke("Float Canoe", "<col=ffff>Canoe Station", 12166, 3, sceneX, sceneY, false);
											});
											//if(!v.getVars().getBoolean("FLOATCANOE")) {
											//	v.invoke("Float Canoe", "<col=ffff>Canoe Station", 12166, 3, sceneX, sceneY, false);
											//	v.getVars().setBoolean("FLOATCANOE", true);
											//	v.getCallbacks().afterTicks(10, ()-> {
											//		v.getVars().setBoolean("FLOATCANOE", false);
											//	});
											//}
										} else if(imposterComp.getId() == 12144) {
											v.exec("CHOPPING", 10, () -> {
												v.invoke("Chop-down", "<col=ffff>Canoe Station", 12166, 3, sceneX, sceneY, false);
											});
											//if(!v.getVars().getBoolean("CHOPPING")) {
											//	v.invoke("Chop-down", "<col=ffff>Canoe Station", 12166, 3, sceneX, sceneY, false);
											//	v.getVars().setBoolean("CHOPPING", true);
											//	v.getCallbacks().afterTicks(10, ()-> {
											//		v.getVars().setBoolean("CHOPPING", false);
											//	});
											//}
										} else if(imposterComp.getId() == 12146) {
											v.exec("SHAPECANOE", 10, () -> {
												v.invoke("Shape-Canoe", "<col=ffff>Canoe Station", 12166, 3, sceneX, sceneY, false);
											});
											//if(!v.getVars().getBoolean("SHAPECANOE")) {
											//	v.invoke("Shape-Canoe", "<col=ffff>Canoe Station", 12166, 3, sceneX, sceneY, false);
											//	v.getVars().setBoolean("SHAPECANOE", true);
											//	v.getCallbacks().afterTicks(10, ()-> {
											//		v.getVars().setBoolean("SHAPECANOE", false);
											//	});
											//}
										} else if(imposterComp.getId() == 12158)  {
											v.exec("PADDLECANOE", 10, () -> {
												v.invoke("Paddle Canoe", "<col=ffff>Canoe Station", 12166, 3, sceneX, sceneY, false);
											});
											//if(!v.getVars().getBoolean("PADDLECANOE")) {
											//	v.invoke("Paddle Canoe", "<col=ffff>Canoe Station", 12166, 3, sceneX, sceneY, false);
											//	v.getVars().setBoolean("PADDLECANOE", true);
											//	v.getCallbacks().afterTicks(10, ()-> {
											//		v.getVars().setBoolean("PADDLECANOE", false);
											//	});
											//}
										} 
									}
								}
							} 
						  }
					}
				}
			}
		}
		
	} else if (v.getWalking().nearEntity(Entity.GAME, 10355, 25)) {
		log.info("WE ARE AT EDGE BANK");
			v.getPrayer().prayOff(Prayer.PROTECT_FROM_MAGIC);
			if(v.getEquipment().hasEquipped(1704)) {
				v.getEquipment().unequip(KitType.AMULET);
			} else if(v.getEquipment().hasEquipped(2572)) {
				v.getEquipment().unequipRing(2572);
			} else if(v.getInventory().amountInInventory(11959, 1, 1000)) {
				v.getBank().depositAll();
			} else if(v.getInventory().hasIn(1704)) {
				v.getBank().deposit(1704,1);
			} else if(v.getInventory().hasIn(2572)) {
				v.getBank().deposit(2572,1);
			} else if(!v.getEquipment().hasEquipped(1706,1708,1710,1712,8283,10354,10356,10358,10360,10362,11964,11966,11976,11978,20586)) {
				if(v.getInventory().hasIn(11978)) {
					v.getBank().equip(11978);
				} else {
					if(!v.getBank().isOpen()) {
						v.getBank().open();
					} else {
						v.exec("WITHDRAW_GLORY", 3, () -> {
							v.getBank().withdraw(11978, 1);
						});
					}
				}
			} else if(!v.getEquipment().hasEquipped(11980,11982,11984,11986,11988,12785,20786,20787,20788,20789,20790)) {
			//v.getEquipment().unequip(KitType.HANDS);
				if(v.getInventory().hasIn(11980)) {
					v.getBank().equip(11980);
				} else {
					v.getBank().withdraw(11980, 1);
				}
		} else {
			if(v.getInventory().slot(10008) == -1) {
				if(!v.getVars().getBoolean("WITHDRAWING")) {
					v.getBank().withdraw(10008, 10);
					v.getVars().setBoolean("WITHDRAWING", true);
					v.getCallbacks().afterTicks(3, ()-> {
						v.getVars().setBoolean("WITHDRAWING", false);
					});
				}
			} else {
				if (!v.getWalking().isMoving() && v.getLocalPlayer().hasAnimation(-1)) {
					//v.getInventory().equip(11984,11986,11988,11982,1359,1704,1706,1708,1710,1712,8283,10354,10356,10358,10360,10362,11964,11966,11976,11978,20586);
					v.getWalking().walk(new WorldPoint(3133, 3510, 0));
				}
			}
		}

	} else if (v.getWalking().nearPosition(new WorldPoint(3221,3219, 0), 25)) {
		v.getInventory().equip(11984,11986,11988,11982,1359,1704,1706,1708,1710,1712,8283,10354,10356,10358,10360,10362,11964,11966,11976,11978,20586,11980);
		v.exec("WEDIED", 5, () -> {
			v.getEquipment().selectOption(EquipmentInventorySlot.RING, "Grand Exchange", 3);
		});
	} else if(v.getWalking().nearPosition(new WorldPoint(3163,3481,0), 15)) {
		if(!v.getInventory().hasIn(boxTrapId)) {
			if(!v.getWalking().isMoving() && v.getLocalPlayer().hasAnimation(-1)) {
				if(v.getInventory().slot(10008) == -1) {
					if(!v.getVars().getBoolean("WITHDRAWING")) {
						v.getBank().withdraw(10008, 10);
						v.getVars().setBoolean("WITHDRAWING", true);
						v.getCallbacks().afterTicks(3, ()-> {
							v.getVars().setBoolean("WITHDRAWING", false);
						});
					}
				}
			}
		} else {
			if(!v.getWalking().isMoving() && v.getLocalPlayer().hasAnimation(-1)) {
				v.getWalking().walkR(new WorldPoint(3145,3510,0), 3);
			}
		}

	} else if(v.getWalking().nearEntity(Entity.GAME,16530, 8)) {
		if(!v.getWalking().isMoving() && v.getLocalPlayer().hasAnimation(-1)) {
			GameObject obj = v.getGameObject().findNearest(16530);
			if(obj != null) {
				int agilityX = obj.getSceneMinLocation().getX();
				int agilityY = obj.getSceneMinLocation().getY();
				v.invoke("Climb-into","<col=ffff>Underwall tunnel",16530,3,agilityX,agilityY,false);
			}

		}
	}
	
} else {
	int currentLogoutCounter = v.getVars().getInt("LOGOUT_TIMER");
	if (currentLogoutCounter > 11) {

		v.getGame().login(username, password, client.getWorld());

	} else {
		v.getGeneral().flushQueue();
		v.getVars().setInt("LOGOUT_TIMER", currentLogoutCounter + 1);
	}
}
