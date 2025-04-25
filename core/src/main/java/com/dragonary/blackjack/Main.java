package com.dragonary.blackjack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private Deck deck;
    private Hand p1, p2;
    private boolean running = true;
    private Label p1ScoreLabel, p2ScoreLabel, messageLabel;
    private Table table;
    private int currentPlayer = 1;
    private boolean p1Done = false;
    private boolean p2Done = false;
    private Label turnLabel;
    private TextButton restartButton;

    @Override
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        deck = new Deck();
        deck.shuffle();
        deck.initTextures();
        p1 = new Hand();
        p2 = new Hand();
        p1.addCard(deck.drawCard());
        p2.addCard(deck.drawCard());

        p1ScoreLabel = new Label("Player 1 score: " + p1.getTotalValue(), skin);
        p2ScoreLabel = new Label("Player 2 score: " + p2.getTotalValue(), skin);
        messageLabel = new Label("", skin);
        turnLabel = new Label("Player 1's turn", skin);
        TextButton hitButton = new TextButton("Hit", skin);
        TextButton standButton = new TextButton("Stand", skin);
        restartButton = new TextButton("Restart", skin);
        restartButton.setVisible(false);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restartGame();
            }
        });

        hitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!running) return;
                if (currentPlayer == 1 && !p1Done) {
                    p1.addCard(deck.drawCard());
                    updateScores();
                    if (p1.getTotalValue() > 21) {
                        p1Done = true;
                        messageLabel.setText("Player 1 bust! Player 2 wins!");
                        turnLabel.setText("Game Over");
                        running = false;
                        restartButton.setVisible(true);
                        return;
                    }
                    nextPlayer();
                } else if (currentPlayer == 2 && !p2Done) {
                    p2.addCard(deck.drawCard());
                    updateScores();
                    if (p2.getTotalValue() > 21) {
                        p2Done = true;
                        messageLabel.setText("Player 2 bust! Player 1 wins!");
                        turnLabel.setText("Game Over");
                        running = false;
                        restartButton.setVisible(true);
                        return;
                    }
                    nextPlayer();
                }
            }
        });
        standButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!running) return;
                if (currentPlayer == 1 && !p1Done) {
                    p1Done = true;
                    messageLabel.setText("Player 1 stands. Player 2's turn.");
                    nextPlayer();
                } else if (currentPlayer == 2 && !p2Done) {
                    p2Done = true;
                    messageLabel.setText("Player 2 stands. Player 1's turn.");
                    nextPlayer();
                }
            }
        });

        table = new Table();
        table.setFillParent(true);
        table.top();
        table.add(p1ScoreLabel).pad(10);
        table.add(p2ScoreLabel).pad(10);
        table.row();
        table.add(turnLabel).colspan(2).pad(10);
        table.row();
        table.add(hitButton).pad(10).width(70).height(40);
        table.add(standButton).pad(10).width(70).height(40);
        table.row();
        table.add(messageLabel).colspan(2).pad(10);
        table.row();
        table.add(restartButton).colspan(2).pad(10).width(70).height(40);
        stage.addActor(table);
    }

    private void updateScores() {
        p1ScoreLabel.setText("Player 1 score: " + p1.getTotalValue());
        p2ScoreLabel.setText("Player 2 score: " + p2.getTotalValue());
    }

    private void nextPlayer() {
        if (p1Done && p2Done) {
            endGame();
        } else if (!p1Done && !p2Done) {
            if (currentPlayer == 1) {
                currentPlayer = 2;
                turnLabel.setText("Player 2's turn");
            } else {
                currentPlayer = 1;
                turnLabel.setText("Player 1's turn");
            }
        } else if (!p1Done) {
            currentPlayer = 1;
            turnLabel.setText("Player 1's turn");
        } else if (!p2Done) {
            currentPlayer = 2;
            turnLabel.setText("Player 2's turn");
        }
        updateScores();
    }

    private void endGame() {
        running = false;
        int p1Score = p1.getTotalValue();
        int p2Score = p2.getTotalValue();
        if (p1Score > 21 && p2Score > 21) {
            messageLabel.setText("Both players bust! It's a tie!");
        } else if (p1Score > 21) {
            messageLabel.setText("Player 1 busts! Player 2 wins!");
        } else if (p2Score > 21) {
            messageLabel.setText("Player 2 busts! Player 1 wins!");
        } else if (p1Score > p2Score) {
            messageLabel.setText("Game end. Player 1 wins!");
        } else if (p1Score < p2Score) {
            messageLabel.setText("Game end. Player 2 wins!");
        } else {
            messageLabel.setText("Game end. It's a tie!");
        }
        turnLabel.setText("Game Over");
        restartButton.setVisible(true);
    }

    private void restartGame() {
        deck = new Deck();
        deck.shuffle();
        deck.initTextures();
        p1 = new Hand();
        p2 = new Hand();
        p1.addCard(deck.drawCard());
        p2.addCard(deck.drawCard());
        p1Done = false;
        p2Done = false;
        currentPlayer = 1;
        running = true;
        messageLabel.setText("");
        turnLabel.setText("Player 1's turn");
        updateScores();
        restartButton.setVisible(false);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.begin();
        // Draw Player 1's hand
        int x = 50;
        int y = 300;
        for (Card c : p1.getCards()) {
            batch.draw(c.getTexture(), x, y, c.getTexture().getWidth() * 0.2f, c.getTexture().getHeight() * 0.2f);
            x += 50;
        }
        // Draw Player 2's hand
        x = 50;
        y = 100;
        for (Card c : p2.getCards()) {
            batch.draw(c.getTexture(), x, y, c.getTexture().getWidth() * 0.2f, c.getTexture().getHeight() * 0.2f);
            x += 50;
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        skin.dispose();
    }
}