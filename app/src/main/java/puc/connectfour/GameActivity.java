package puc.connectfour;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {

    private Drawable bgEmpty, bgRed, bgYellow;
    private ImageView img1, img2, img3, img4, img5, img6, img7;
    private ImageButton btnLeft, btnDown, btnRight;
    private Button btnStart;
    private Button btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18;
    private Button btn21, btn22, btn23, btn24, btn25, btn26, btn27, btn28;
    private Button btn31, btn32, btn33, btn34, btn35, btn36, btn37, btn38;
    private Button btn41, btn42, btn43, btn44, btn45, btn46, btn47, btn48;
    private Button btn51, btn52, btn53, btn54, btn55, btn56, btn57, btn58;
    private Button btn61, btn62, btn63, btn64, btn65, btn66, btn67, btn68;
    private int cabecalho = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define os 3 layouts para controle
        bgEmpty = getDrawable(R.drawable.bg_circle_empty);
        bgRed = getDrawable(R.drawable.bg_circle_red);
        bgYellow = getDrawable(R.drawable.bg_circle_yellow);

        // Linka os elementos da tela com o Java
        linkScreenElements();

        // Acao dos botoes
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveLeft();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRight();
            }
        });
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveDown();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }

    public void moveLeft() {
        if (cabecalho == 1) {
            // Nao se move pois esta na margem
            // ++ apenas para anular o -- no final
            cabecalho++;
        } else if (cabecalho == 2) {
            img1.setBackground(bgRed);
            img2.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (cabecalho == 3) {
            img2.setBackground(bgRed);
            img3.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (cabecalho == 4) {
            img3.setBackground(bgRed);
            img4.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (cabecalho == 5) {
            img4.setBackground(bgRed);
            img5.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (cabecalho == 6) {
            img5.setBackground(bgRed);
            img6.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (cabecalho == 7) {
            img6.setBackground(bgRed);
            img7.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        }

        // Marca que o cabelho voltou uma posicao
        cabecalho--;
    }

    public void moveRight() {
        if (cabecalho == 1) {
            img2.setBackground(bgRed);
            img1.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (cabecalho == 2) {
            img3.setBackground(bgRed);
            img2.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (cabecalho == 3) {
            img4.setBackground(bgRed);
            img3.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (cabecalho == 4) {
            img5.setBackground(bgRed);
            img4.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (cabecalho == 5) {
            img6.setBackground(bgRed);
            img5.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (cabecalho == 6) {
            img7.setBackground(bgRed);
            img6.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (cabecalho == 7) {
            // Nao se move pois esta na margem
            // -- apenas para anular o ++ no final
            cabecalho--;
        }

        // Marca que o cabelho avancou uma posicao
        cabecalho++;
    }

    public void moveDown() {
        if (cabecalho == 1) {
            btn61.setBackground(bgRed);
        } else if (cabecalho == 2) {
            btn62.setBackground(bgRed);
        } else if (cabecalho == 3) {
            btn63.setBackground(bgRed);
        } else if (cabecalho == 4) {
            btn64.setBackground(bgRed);
        } else if (cabecalho == 5) {
            btn65.setBackground(bgRed);
        } else if (cabecalho == 6) {
            btn66.setBackground(bgRed);
        } else if (cabecalho == 7) {
            btn67.setBackground(bgRed);
        }
    }

    public void startGame() {
        // Temporario
        btn61.setBackground(bgEmpty);
        btn62.setBackground(bgEmpty);
        btn63.setBackground(bgEmpty);
        btn64.setBackground(bgEmpty);
        btn65.setBackground(bgEmpty);
        btn66.setBackground(bgEmpty);
        btn67.setBackground(bgEmpty);
    }

    public void linkScreenElements() {
        // Botoes de acao
        btnLeft = (ImageButton) findViewById(R.id.btnLeft);
        btnDown = (ImageButton) findViewById(R.id.btnDown);
        btnRight = (ImageButton) findViewById(R.id.btnRight);
        btnStart = (Button) findViewById(R.id.btnStart);

        // Cabecalho
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        img6 = (ImageView) findViewById(R.id.img6);
        img7 = (ImageView) findViewById(R.id.img7);

        // ---Tabuleiro
        // Linha 1
        btn11 = (Button) findViewById(R.id.btn11);
        btn12 = (Button) findViewById(R.id.btn12);
        btn13 = (Button) findViewById(R.id.btn13);
        btn14 = (Button) findViewById(R.id.btn14);
        btn15 = (Button) findViewById(R.id.btn15);
        btn16 = (Button) findViewById(R.id.btn16);
        btn17 = (Button) findViewById(R.id.btn17);
        // Linha 2
        btn21 = (Button) findViewById(R.id.btn21);
        btn22 = (Button) findViewById(R.id.btn22);
        btn23 = (Button) findViewById(R.id.btn23);
        btn24 = (Button) findViewById(R.id.btn24);
        btn25 = (Button) findViewById(R.id.btn25);
        btn26 = (Button) findViewById(R.id.btn26);
        btn27 = (Button) findViewById(R.id.btn27);
        // Linha 3
        btn31 = (Button) findViewById(R.id.btn31);
        btn32 = (Button) findViewById(R.id.btn32);
        btn33 = (Button) findViewById(R.id.btn33);
        btn34 = (Button) findViewById(R.id.btn34);
        btn35 = (Button) findViewById(R.id.btn35);
        btn36 = (Button) findViewById(R.id.btn36);
        btn37 = (Button) findViewById(R.id.btn37);
        // Linha 4
        btn41 = (Button) findViewById(R.id.btn41);
        btn42 = (Button) findViewById(R.id.btn42);
        btn43 = (Button) findViewById(R.id.btn43);
        btn44 = (Button) findViewById(R.id.btn44);
        btn45 = (Button) findViewById(R.id.btn45);
        btn46 = (Button) findViewById(R.id.btn46);
        btn47 = (Button) findViewById(R.id.btn47);
        // Linha 5
        btn51 = (Button) findViewById(R.id.btn51);
        btn52 = (Button) findViewById(R.id.btn52);
        btn53 = (Button) findViewById(R.id.btn53);
        btn54 = (Button) findViewById(R.id.btn54);
        btn55 = (Button) findViewById(R.id.btn55);
        btn56 = (Button) findViewById(R.id.btn56);
        btn57 = (Button) findViewById(R.id.btn57);
        // Linha 6
        btn61 = (Button) findViewById(R.id.btn61);
        btn62 = (Button) findViewById(R.id.btn62);
        btn63 = (Button) findViewById(R.id.btn63);
        btn64 = (Button) findViewById(R.id.btn64);
        btn65 = (Button) findViewById(R.id.btn65);
        btn66 = (Button) findViewById(R.id.btn66);
        btn67 = (Button) findViewById(R.id.btn67);
    }
}
