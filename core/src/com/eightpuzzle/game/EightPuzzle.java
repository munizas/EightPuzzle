package com.eightpuzzle.game;



/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
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
 ******************************************************************************/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class EightPuzzle implements ApplicationListener {
	Skin skin;
	Stage stage;
	final Table table = new Table();

	@Override
	public void create () {
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		Gdx.input.setInputProcessor(stage);

		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());

		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		//textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		
		Label.LabelStyle labelSty = new Label.LabelStyle();
		labelSty.font = skin.getFont("default");
		//labelSty.fontColor = Color.GREEN;
		skin.add("default", labelSty);
		
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Zig.ttf"));

		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 50;

		BitmapFont gameFont = generator.generateFont(parameter);
		generator.dispose();
		
		FileHandle blue, n1, n2, n3, n4, n5, n6, n7, n8, solveUp, solveDown;
		if (Gdx.app.getType() == ApplicationType.Desktop) {
			blue = Gdx.files.internal("icons64px/blue-square.png");
			n1 = Gdx.files.internal("icons64px/Number-1-icon.png");
			n2 = Gdx.files.internal("icons64px/Number-2-icon.png");
			n3 = Gdx.files.internal("icons64px/Number-3-icon.png");
			n4 = Gdx.files.internal("icons64px/Number-4-icon.png");
			n5 = Gdx.files.internal("icons64px/Number-5-icon.png");
			n6 = Gdx.files.internal("icons64px/Number-6-icon.png");
			n7 = Gdx.files.internal("icons64px/Number-7-icon.png");
			n8 = Gdx.files.internal("icons64px/Number-8-icon.png");
			solveUp = blue;
			solveDown = blue;
		}
		else {
			blue = Gdx.files.internal("icons256px/blue-circle.png");
			n1 = Gdx.files.internal("icons256px/Number-1-icon.png");
			n2 = Gdx.files.internal("icons256px/Number-2-icon.png");
			n3 = Gdx.files.internal("icons256px/Number-3-icon.png");
			n4 = Gdx.files.internal("icons256px/Number-4-icon.png");
			n5 = Gdx.files.internal("icons256px/Number-5-icon.png");
			n6 = Gdx.files.internal("icons256px/Number-6-icon.png");
			n7 = Gdx.files.internal("icons256px/Number-7-icon.png");
			n8 = Gdx.files.internal("icons256px/Number-8-icon.png");
			solveUp = Gdx.files.internal("icons256px/Box_Green.png");
			solveDown = Gdx.files.internal("icons256px/rectangle_green.png");
		}
		Texture bSquare = new Texture(blue);
		Texture n1T = new Texture(n1);
		Texture n2T = new Texture(n2);
		Texture n3T = new Texture(n3);
		Texture n4T = new Texture(n4);
		Texture n5T = new Texture(n5);
		Texture n6T = new Texture(n6);
		Texture n7T = new Texture(n7);
		Texture n8T = new Texture(n8);
		
		TextureRegion bSquareReg = new TextureRegion(bSquare);
		TextureRegion n1Reg = new TextureRegion(n1T);
		TextureRegion n2Reg = new TextureRegion(n2T);
		TextureRegion n3Reg = new TextureRegion(n3T);
		TextureRegion n4Reg = new TextureRegion(n4T);
		TextureRegion n5Reg = new TextureRegion(n5T);
		TextureRegion n6Reg = new TextureRegion(n6T);
		TextureRegion n7Reg = new TextureRegion(n7T);
		TextureRegion n8Reg = new TextureRegion(n8T);
		
		TextureRegionDrawable solveDUp = new TextureRegionDrawable(new TextureRegion(new Texture(solveUp)));
		TextureRegionDrawable solveDDown = new TextureRegionDrawable(new TextureRegion(new Texture(solveDown)));
		ImageTextButton.ImageTextButtonStyle tbSty = new ImageTextButton.ImageTextButtonStyle(solveDUp, solveDDown, solveDUp, gameFont);
		skin.add("default", tbSty);
		
		ImageButtonStyle bSquareSty = new ImageButtonStyle();
		bSquareSty.imageUp = new TextureRegionDrawable(bSquareReg);
		bSquareSty.imageDown = new TextureRegionDrawable(bSquareReg);
		
		ImageButtonStyle n1Sty = new ImageButtonStyle();
		n1Sty.imageUp = new TextureRegionDrawable(n1Reg);
		n1Sty.imageDown = new TextureRegionDrawable(n1Reg);
		
		ImageButtonStyle n2Sty = new ImageButtonStyle();
		n2Sty.imageUp = new TextureRegionDrawable(n2Reg);
		n2Sty.imageDown = new TextureRegionDrawable(n2Reg);
		
		ImageButtonStyle n3Sty = new ImageButtonStyle();
		n3Sty.imageUp = new TextureRegionDrawable(n3Reg);
		n3Sty.imageDown = new TextureRegionDrawable(n3Reg);
		
		ImageButtonStyle n4Sty = new ImageButtonStyle();
		n4Sty.imageUp = new TextureRegionDrawable(n4Reg);
		n4Sty.imageDown = new TextureRegionDrawable(n4Reg);
		
		ImageButtonStyle n5Sty = new ImageButtonStyle();
		n5Sty.imageUp = new TextureRegionDrawable(n5Reg);
		n5Sty.imageDown = new TextureRegionDrawable(n5Reg);
		
		ImageButtonStyle n6Sty = new ImageButtonStyle();
		n6Sty.imageUp = new TextureRegionDrawable(n6Reg);
		n6Sty.imageDown = new TextureRegionDrawable(n6Reg);
		
		ImageButtonStyle n7Sty = new ImageButtonStyle();
		n7Sty.imageUp = new TextureRegionDrawable(n7Reg);
		n7Sty.imageDown = new TextureRegionDrawable(n7Reg);
		
		ImageButtonStyle n8Sty = new ImageButtonStyle();
		n8Sty.imageUp = new TextureRegionDrawable(n8Reg);
		n8Sty.imageDown = new TextureRegionDrawable(n8Reg);
		
		ImageButton b1 = new ImageButton(n1Sty);
		b1.addListener(new MyChangeListener(1));
		map.put(1, b1);
		
		ImageButton holeB = new ImageButton(bSquareSty);
		map.put(0, holeB);
		
		ImageButton ib = new ImageButton(n2Sty);
		ib.addListener(new MyChangeListener(2));
		map.put(2, ib);
		
		ImageButton b4 = new ImageButton(n3Sty);
		b4.addListener(new MyChangeListener(3));
		ImageButton b5 = new ImageButton(n4Sty);
		b5.addListener(new MyChangeListener(4));
		ImageButton b6 = new ImageButton(n5Sty);
		b6.addListener(new MyChangeListener(5));
		map.put(3, b4);
		map.put(4, b5);
		map.put(5, b6);
		
		ImageButton b7 = new ImageButton(n6Sty);
		b7.addListener(new MyChangeListener(6));
		ImageButton b8 = new ImageButton(n7Sty);
		b8.addListener(new MyChangeListener(7));
		ImageButton b9 = new ImageButton(n8Sty);
		b9.addListener(new MyChangeListener(8));
		map.put(6, b7);
		map.put(7, b8);
		map.put(8, b9);

		ImageTextButton newGameB = new ImageTextButton("New Game", skin);
		newGameB.addListener(new NewGameListener());

		ImageTextButton solveB = new ImageTextButton("Solve", skin);
		solveB.addListener(new MySolveListener());
		
		newGameBoard();
		
		table.padTop(50);
		table.padBottom(20);
		
		VerticalGroup vg = new VerticalGroup();
		vg.padTop(50);
		vg.setFillParent(true);
		vg.addActor(new Label("Moves", skin));
		vg.addActor(newGameB);
		vg.addActor(table);
		vg.addActor(solveB);
		
		stage.addActor(vg);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}
	
	private void newGameBoard() {
		state = EightPuzzleGenerator.newPuzzle();
		holeIndex = state.lastIndexOf(0);
		updateTable();
	}

	private void updateTable() {
		for (int i = 0; i < state.size(); i+=3) {
			table.add(map.get(state.get(i)));
			table.add(map.get(state.get(i+1)));
			table.add(map.get(state.get(i+2)));
			table.row().fillX();
		}
	}
	
	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose () {
		stage.dispose();
		skin.dispose();
	}
	
	private HashMap<Integer, Button> map = new HashMap<Integer, Button>();
	private List<Integer> state = new ArrayList<Integer>();
	private int holeIndex;
	
	public class MyChangeListener extends ChangeListener {
		private int value;
		public MyChangeListener(int val) {
			value = val;
		}
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			int index = state.indexOf(value);
			boolean isLegalMove = false;
			if (holeIndex == index-3 || holeIndex == index+3)
				isLegalMove = true;
			else if ((index != 2 && index != 5) && holeIndex == index+1)
				isLegalMove = true;
			else if ((index != 3 && index != 6) && holeIndex == index-1)
				isLegalMove = true;
			
			if (isLegalMove) {
				int tmp = state.get(index);
				state.set(index, state.get(holeIndex));
				state.set(holeIndex, tmp);
				tmp = holeIndex;
				holeIndex = index;
				//value = tmp;
				updateTable();
			}
		}
		
	}
	
	public class MySolveListener extends ClickListener {
		@Override
		public void clicked(InputEvent a, float x, float y) {
			map.get(1).toggle();
		}
	}
	
	private class NewGameListener extends ClickListener {
		@Override
		public void clicked(InputEvent ie, float x, float y) {
			newGameBoard();
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}