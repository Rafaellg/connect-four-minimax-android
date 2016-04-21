package puc.connectfour;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private Drawable bgEmpty, bgRed, bgYellow;
    private ImageView img1, img2, img3, img4, img5, img6, img7;
    private ImageButton btnLeft, btnDown, btnRight;
    private Button btnStart;
    private Button btn11, btn12, btn13, btn14, btn15, btn16, btn17;
    private Button btn21, btn22, btn23, btn24, btn25, btn26, btn27;
    private Button btn31, btn32, btn33, btn34, btn35, btn36, btn37;
    private Button btn41, btn42, btn43, btn44, btn45, btn46, btn47;
    private Button btn51, btn52, btn53, btn54, btn55, btn56, btn57;
    private Button btn61, btn62, btn63, btn64, btn65, btn66, btn67;
    private Button[][] boardBtn = new Button[6][7];

    // Posicao inicial default
    private int posCabecalho = 3;
    // Player do momento atual
    private int playerAtual = 1;
    // Matriz do tabuleiro
    private int[][] board = new int[6][7];

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

        // Inicializa o board
        initializeBoard();

        // Acao dos botoes
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        // Esconde os botões de acao
        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.GONE);
        btnDown.setVisibility(View.GONE);
    }

    public void initializeBoard() {
        // Matriz de player
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = 0;
            }
        }

        // Matriz de botoes
        boardBtn[0][0] = btn11;
        boardBtn[1][0] = btn21;
        boardBtn[2][0] = btn31;
        boardBtn[3][0] = btn41;
        boardBtn[4][0] = btn51;
        boardBtn[5][0] = btn61;

        boardBtn[0][1] = btn12;
        boardBtn[1][1] = btn22;
        boardBtn[2][1] = btn32;
        boardBtn[3][1] = btn42;
        boardBtn[4][1] = btn52;
        boardBtn[5][1] = btn62;

        boardBtn[0][2] = btn13;
        boardBtn[1][2] = btn23;
        boardBtn[2][2] = btn33;
        boardBtn[3][2] = btn43;
        boardBtn[4][2] = btn53;
        boardBtn[5][2] = btn63;

        boardBtn[0][3] = btn14;
        boardBtn[1][3] = btn24;
        boardBtn[2][3] = btn34;
        boardBtn[3][3] = btn44;
        boardBtn[4][3] = btn54;
        boardBtn[5][3] = btn64;

        boardBtn[0][4] = btn15;
        boardBtn[1][4] = btn25;
        boardBtn[2][4] = btn35;
        boardBtn[3][4] = btn45;
        boardBtn[4][4] = btn55;
        boardBtn[5][4] = btn65;

        boardBtn[0][5] = btn16;
        boardBtn[1][5] = btn26;
        boardBtn[2][5] = btn36;
        boardBtn[3][5] = btn46;
        boardBtn[4][5] = btn56;
        boardBtn[5][5] = btn66;

        boardBtn[0][6] = btn17;
        boardBtn[1][6] = btn27;
        boardBtn[2][6] = btn37;
        boardBtn[3][6] = btn47;
        boardBtn[4][6] = btn57;
        boardBtn[5][6] = btn67;

        // Limpa as cores dos botoes;
        // ---Tabuleiro
        // Linha 1
        btn11.setBackground(bgEmpty);
        btn12.setBackground(bgEmpty);
        btn13.setBackground(bgEmpty);
        btn14.setBackground(bgEmpty);
        btn15.setBackground(bgEmpty);
        btn16.setBackground(bgEmpty);
        btn17.setBackground(bgEmpty);
        // Linha 2
        btn21.setBackground(bgEmpty);
        btn22.setBackground(bgEmpty);
        btn23.setBackground(bgEmpty);
        btn24.setBackground(bgEmpty);
        btn25.setBackground(bgEmpty);
        btn26.setBackground(bgEmpty);
        btn27.setBackground(bgEmpty);
        // Linha 3
        btn31.setBackground(bgEmpty);
        btn32.setBackground(bgEmpty);
        btn33.setBackground(bgEmpty);
        btn34.setBackground(bgEmpty);
        btn35.setBackground(bgEmpty);
        btn36.setBackground(bgEmpty);
        btn37.setBackground(bgEmpty);
        // Linha 4
        btn41.setBackground(bgEmpty);
        btn42.setBackground(bgEmpty);
        btn43.setBackground(bgEmpty);
        btn44.setBackground(bgEmpty);
        btn45.setBackground(bgEmpty);
        btn46.setBackground(bgEmpty);
        btn47.setBackground(bgEmpty);
        // Linha 5
        btn51.setBackground(bgEmpty);
        btn52.setBackground(bgEmpty);
        btn53.setBackground(bgEmpty);
        btn54.setBackground(bgEmpty);
        btn55.setBackground(bgEmpty);
        btn56.setBackground(bgEmpty);
        btn57.setBackground(bgEmpty);
        // Linha 6
        btn61.setBackground(bgEmpty);
        btn62.setBackground(bgEmpty);
        btn63.setBackground(bgEmpty);
        btn64.setBackground(bgEmpty);
        btn65.setBackground(bgEmpty);
        btn66.setBackground(bgEmpty);
        btn67.setBackground(bgEmpty);
    }

    public void moveLeft() {
        if (posCabecalho == 0) {
            // Nao se move pois esta na margem
            // ++ apenas para anular o -- no final
            posCabecalho++;
        } else if (posCabecalho == 1) {
            if (playerAtual == 1) {
                img1.setBackground(bgRed);
            } else {
                img1.setBackground(bgYellow);
            }
            img2.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 2) {
            if (playerAtual == 1) {
                img2.setBackground(bgRed);
            } else {
                img2.setBackground(bgYellow);
            }
            img3.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 3) {
            if (playerAtual == 1) {
                img3.setBackground(bgRed);
            } else {
                img3.setBackground(bgYellow);
            }
            img4.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 4) {
            if (playerAtual == 1) {
                img4.setBackground(bgRed);
            } else {
                img4.setBackground(bgYellow);
            }
            img5.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 5) {
            if (playerAtual == 1) {
                img5.setBackground(bgRed);
            } else {
                img5.setBackground(bgYellow);
            }
            img6.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 6) {
            if (playerAtual == 1) {
                img6.setBackground(bgRed);
            } else {
                img6.setBackground(bgYellow);
            }
            img7.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        }

        // Marca que o cabelho voltou uma posicao
        posCabecalho--;
    }

    public void moveRight() {
        if (posCabecalho == 0) {
            if (playerAtual == 1) {
                img2.setBackground(bgRed);
            } else {
                img2.setBackground(bgYellow);
            }
            img1.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 1) {
            if (playerAtual == 1) {
                img3.setBackground(bgRed);
            } else {
                img3.setBackground(bgYellow);
            }
            img2.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 2) {
            if (playerAtual == 1) {
                img4.setBackground(bgRed);
            } else {
                img4.setBackground(bgYellow);
            }
            img3.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 3) {
            if (playerAtual == 1) {
                img5.setBackground(bgRed);
            } else {
                img5.setBackground(bgYellow);
            }
            img4.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 4) {
            if (playerAtual == 1) {
                img6.setBackground(bgRed);
            } else {
                img6.setBackground(bgYellow);
            }
            img5.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 5) {
            if (playerAtual == 1) {
                img7.setBackground(bgRed);
            } else {
                img7.setBackground(bgYellow);
            }
            img6.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 6) {
            // Nao se move pois esta na margem
            // -- apenas para anular o ++ no final
            posCabecalho--;
        }

        // Marca que o cabelho avancou uma posicao
        posCabecalho++;
    }

    public void moveDown() {
        boolean edit = false;

        for (int i = 5; i >= 0; i--) {
            if (board[i][posCabecalho] == 0) {
                board[i][posCabecalho] = playerAtual;

                // Marca que ocorreu uma edicao
                edit = true;

                // Sai do for
                i = -1;
            }
        }

        // Verifica se aconteceu alguma alteracao
        if (edit) {
            // Atualiza o botao do cabecalho
            if (posCabecalho == 0) {
                if (playerAtual == 1) {
                    img1.setBackground(bgRed);
                } else {
                    img1.setBackground(bgYellow);
                }
            } else if (posCabecalho == 1) {
                if (playerAtual == 1) {
                    img2.setBackground(bgRed);
                } else {
                    img2.setBackground(bgYellow);
                }
            } else if (posCabecalho == 2) {
                if (playerAtual == 1) {
                    img3.setBackground(bgRed);
                } else {
                    img3.setBackground(bgYellow);
                }
            } else if (posCabecalho == 3) {
                if (playerAtual == 1) {
                    img4.setBackground(bgRed);
                } else {
                    img4.setBackground(bgYellow);
                }
            } else if (posCabecalho == 4) {
                if (playerAtual == 1) {
                    img5.setBackground(bgRed);
                } else {
                    img5.setBackground(bgYellow);
                }
            } else if (posCabecalho == 5) {
                if (playerAtual == 1) {
                    img6.setBackground(bgRed);
                } else {
                    img6.setBackground(bgYellow);
                }
            } else if (posCabecalho == 6) {
                if (playerAtual == 1) {
                    img7.setBackground(bgRed);
                } else {
                    img7.setBackground(bgYellow);
                }
            }

            // Atualiza o tabuleiro
            showBoard();

            // Verifica se venceu
            if (win()) {
                btnLeft.setOnClickListener(null);
                btnRight.setOnClickListener(null);
                btnDown.setOnClickListener(null);

                btnStart.setVisibility(View.VISIBLE);

                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.GONE);
                btnDown.setVisibility(View.GONE);

                if (playerAtual == 1) {
                    Toast.makeText(this, "Vermelho venceu!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Amarelo venceu!", Toast.LENGTH_SHORT).show();
                }
            }

            // Troca o jogador atual
            playerAtual = (playerAtual%2)+1;
        } else {
            Toast.makeText(this, "Coluna cheia!", Toast.LENGTH_SHORT).show();
        }
    }

    public void showBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == 1) {
                    boardBtn[i][j].setBackground(bgRed);
                } else if (board[i][j] == 2) {
                    boardBtn[i][j].setBackground(bgYellow);
                }
            }
        }
    }

    public void startGame() {
        btnStart.setVisibility(View.GONE);

        btnLeft.setVisibility(View.VISIBLE);
        btnRight.setVisibility(View.VISIBLE);
        btnDown.setVisibility(View.VISIBLE);

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

        initializeBoard();
        showBoard();
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

    public boolean win() {
        boolean win = false;
        //Check for horizontal win
        for (int row=0;row<6;row++)
        {
            for (int col = 0;col<4;col++)
            {
                if (board[row][col] != 0 &&
                        board[row][col] == board[row][col+1] &&
                        board[row][col] == board[row][col+2] &&
                        board[row][col] == board[row][col+3])
                {
                    win = true;
                }
            }
        }
        //Check for vertical win
        for (int row=0;row<3;row++)
        {
            for (int col = 0;col<7;col++)
            {
                if (board[row][col] != 0 &&
                        board[row][col] == board[row+1][col] &&
                        board[row][col] == board[row+2][col] &&
                        board[row][col] == board[row+3][col])
                {
                    win = true;
                }
            }
        }
        //Check for diagonal win (/)
        for (int row=5;row>2;row--)
        {
            for (int col = 0;col<4;col++)
            {
                if (board[row][col] != 0 &&
                        board[row][col] == board[row-1][col+1] &&
                        board[row][col] == board[row-2][col+2] &&
                        board[row][col] == board[row-3][col+3])
                {
                    win = true;
                }
            }
        }
        //Check for diagonal win (\)
        for (int row=0;row<3;row++)
        {
            for (int col = 0;col<4;col++)
            {
                if (board[row][col] != 0 &&
                        board[row][col] == board[row+1][col+1] &&
                        board[row][col] == board[row+2][col+2] &&
                        board[row][col] == board[row+3][col+3])
                {
                    win = true;
                }
            }
        }
        return win;
    }
}
