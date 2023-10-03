package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public  class Creature extends AppCompatActivity {
    protected int attack;
    protected int defense;
    protected int health;
    protected int maxHealth;


    public Creature(int attack, int defense, int health) {
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.maxHealth = health;
    }

    public Creature() {

    }
    public int getAttack() {
        return attack;
    }


    public int getDefense() {
        return defense;
    }


    public int getHealth() {
        return health;
    }

    public int setHealth(int health) {
        this.health = health;
        return health;
    }

    public void heal() {
        int maxHealth = getMaxHealth();
        int currenthealth = getHealth();
        if (health < maxHealth) {
            health += currenthealth * 0.3;
            if (health > maxHealth) {
                health = maxHealth;
            }
        }
    }


    public int getMaxHealth() {
        return 0;
    }

    public int getDamage() {
        return 0;
    }


    public class Monster extends Creature {
        private static final int MIN_DAMAGE = 2;
        private static final int MAX_DAMAGE = 8;

        public Monster(int attack, int defense, int health) {
            super(attack, defense, health);
        }

        @Override
        public int getMaxHealth() {
            return maxHealth;

        }

        @Override
        public int getDamage() {
            return (int) (Math.random() * (MAX_DAMAGE - MIN_DAMAGE + 1)) + MIN_DAMAGE;
        }
    }

    public class Player extends Creature {
        private static final int MIN_DAMAGE = 3;
        private static final int MAX_DAMAGE = 10;

        public Player(int attack, int defense, int health) {
            super(attack, defense, health);
        }

        @Override
        public int getMaxHealth() {
            return maxHealth;

        }


        @Override
        public int getDamage() {
            return (int) (Math.random() * (MAX_DAMAGE - MIN_DAMAGE + 1)) + MIN_DAMAGE;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creature_activity);
        Button monsterButton = findViewById(R.id.monsterButton);
        TextView monsterhealth = findViewById(R.id.monsterhealth);
        Button monsterhealthbutton = findViewById(R.id.buttonhealmonster);
        Button playerButton = findViewById(R.id.playerbutton);
        TextView playerhealth = findViewById(R.id.playerhealth);
        Button playerhealthbutton = findViewById(R.id.playerhealthbutton);
        ProgressBar monsterHealthBar = findViewById(R.id.monsterHealthBar);
        ProgressBar playerHealthBar = findViewById(R.id.playerHealthBar);
        EditText attackinput = findViewById(R.id.attack_input);
        EditText healthInput = findViewById(R.id.health_input);
        EditText defenseInput = findViewById(R.id.defense_input);
        EditText attackinput1 = findViewById(R.id.attack_input1);
        EditText healthInput1 = findViewById(R.id.health_input1);
        EditText defenseInput1 = findViewById(R.id.defense_input1);
        Button restartgame = findViewById(R.id.restartgame);
        Button startgame = findViewById(R.id.startgame);

        startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String attackStr = attackinput.getText().toString();
                String healthStr = healthInput.getText().toString();
                String defenseStr = defenseInput.getText().toString();
                String attackStr1 = attackinput1.getText().toString();
                String healthStr1 = healthInput1.getText().toString();
                String defenseStr1 = defenseInput1.getText().toString();

                if (!validation(attackStr, defenseStr, healthStr, attackStr1, defenseStr1, healthStr1)){
                    return;
                }


                int attack = Integer.parseInt(attackStr);
                int health = Integer.parseInt(healthStr);
                int defense = Integer.parseInt(defenseStr);
                int attack1 = Integer.parseInt(attackStr1);
                int health1 = Integer.parseInt(healthStr1);
                int defense1 = Integer.parseInt(defenseStr1);



                Player player = new Player(attack, defense, health);
                Monster monster = new Monster(attack1, defense1, health1);

                monsterHealthBar.setMax(monster.getMaxHealth());
                monsterHealthBar.setProgress(monster.getHealth());
                playerHealthBar.setMax(player.getMaxHealth());
                playerHealthBar.setProgress(player.getHealth());
                playerhealth.setText("Здоровье игрока: " + player.getHealth());
                monsterhealth.setText("Здоровье монстра: " + monster.getHealth());

                int attackModifier = player.getAttack() - monster.getDefense() + 1;




                int diceCount = Math.max(1, attackModifier);

                final int[] successfulHits = {0};
                final int[] healmonstertime = {0};
                final int[] healplayertime = {0};

                monsterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < diceCount; i++) {
                            int roll = (int) (Math.random() * 6) + 1;
                            if (roll == 5 || roll == 6) {
                                successfulHits[0]++;
                            }
                        }

                        if (successfulHits[0] > 0) {
                            int damage = player.getDamage();
                            monster.setHealth(monster.getHealth() - damage);
                            if (monster.getHealth() <= 0) {
                                monsterHealthBar.setProgress(0);
                                monsterhealth.setText("Монстр погиб");
                            } else {
                                monsterHealthBar.setProgress(monster.getHealth());
                                monsterhealth.setText("Здоровье монстра: " + monster.getHealth());
                            }
                        } else {
                            monsterhealth.setText("Неудачная атака");
                        }
                    }
                });
                monsterhealthbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (monster.getHealth() <= 0) {
                            monsterhealth.setText("Исцеление невозможно");
                        } else if (healmonstertime[0] < 3) {
                            monster.heal();
                            monsterHealthBar.setProgress(monster.getHealth());
                            monsterhealth.setText("Здоровье монстра: " + monster.getHealth());
                            healmonstertime[0]++;

                        }

                    }
                });

                playerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < diceCount; i++) {
                            int roll = (int) (Math.random() * 6) + 1;
                            if (roll == 5 || roll == 6) {
                                successfulHits[0]++;
                            }
                        }

                        if (successfulHits[0] > 0) {
                            int damage = monster.getDamage();
                            player.setHealth(player.getHealth() - damage);
                            if (player.getHealth() <= 0) {
                                playerHealthBar.setProgress(0);
                                playerhealth.setText("Игрок погиб");
                            } else {
                                playerHealthBar.setProgress(player.getHealth());
                                playerhealth.setText("Здоровье игрока: " + player.getHealth());
                            }
                        } else {
                            playerhealth.setText("Неудачная атака");
                        }
                    }
                });
                playerhealthbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (player.getHealth() <= 0) {
                            playerhealth.setText("Исцеление невозможно");
                        } else if (healplayertime[0] < 3) {
                            player.heal();
                            playerHealthBar.setProgress(player.getHealth());
                            playerhealth.setText("Здоровье игрока: " + player.getHealth());
                            healplayertime[0]++;
                        }


                    }
                });


            }
        });

        restartgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartActivity();
            }
        });
    }
    private boolean validation(String attackStr, String defenseStr, String healthStr, String attackStr1, String defenseStr1, String healthStr1) {
        TextView error = findViewById(R.id.error);
        if (attackStr.isEmpty() || defenseStr.isEmpty() || healthStr.isEmpty() || attackStr1.isEmpty() || defenseStr1.isEmpty() || healthStr1.isEmpty()) {
            error.setText("Поля не могут быть пустыми");
            return false;
        }
        int attack = Integer.parseInt(attackStr);
        int health = Integer.parseInt(healthStr);
        int defense = Integer.parseInt(defenseStr);
        int attack1 = Integer.parseInt(attackStr1);
        int health1 = Integer.parseInt(healthStr1);
        int defense1 = Integer.parseInt(defenseStr1);

        if ((attack < 0 || defense < 0 || health < 0 || attack1 < 0 || defense1 < 0 || health1 < 0)) {
            error.setText("Значения не могут быть меньше 0");
            return false;
        }

        if (attack > 30 || attack1 > 30 || defense1 > 30 || defense > 30) {
            error.setText("Значения атаки и защиты не могут превышать 30");
            return false;
        }

        error.setVisibility(View.INVISIBLE);
        return true;
    }

    public void restartActivity() {
        Intent starterIntent = getIntent();
        finish();
        startActivity(starterIntent);
    }

}




