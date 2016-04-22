package puc.connectfour;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    // Elementos da tela
    private TextView txtWinner;
    private Drawable bgEmpty, bgRed, bgYellow, bgRedDark, bgYellowDark;
    private ImageView img1, img2, img3, img4, img5, img6, img7;
    private ImageButton btnLeft, btnDown, btnRight;
    private Button btnMinimax, btnMinimaxAlfaBeta;
    private Button btn11, btn12, btn13, btn14, btn15, btn16, btn17;
    private Button btn21, btn22, btn23, btn24, btn25, btn26, btn27;
    private Button btn31, btn32, btn33, btn34, btn35, btn36, btn37;
    private Button btn41, btn42, btn43, btn44, btn45, btn46, btn47;
    private Button btn51, btn52, btn53, btn54, btn55, btn56, btn57;
    private Button btn61, btn62, btn63, btn64, btn65, btn66, btn67;

    // Posicao inicial default
    private int posCabecalho = 3;
    // Player do momento atual
    private int playerAtual = 1;
    // Matriz do tabuleiro e do botao
    private int[][] board = new int[7][6];
    private Button[][] boardBtn = new Button[7][6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Esconde action bar
        getSupportActionBar().hide();

        // Define os 3 layouts para controle
        bgEmpty = getDrawable(R.drawable.bg_circle_empty);
        bgRed = getDrawable(R.drawable.bg_circle_red);
        bgYellow = getDrawable(R.drawable.bg_circle_yellow);
        bgRedDark = getDrawable(R.drawable.bg_circle_red_stroke);
        bgYellowDark = getDrawable(R.drawable.bg_circle_yellow_stroke);

        // Linka os elementos da tela com o Java
        linkScreenElements();

        // Inicializa o board
        initializeBoard();

        // Acao dos botoes
        btnMinimax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(false);
            }
        });
        btnMinimaxAlfaBeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(true);
            }
        });

        // Esconde os botões de acao
        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.GONE);
        btnDown.setVisibility(View.GONE);
    }

    public void initializeBoard() {
        // Define jogador atual
        playerAtual = 1;

        // Matriz de player
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = 0;
            }
        }

        // Matriz de botoes
        boardBtn[0][0] = btn11;
        boardBtn[0][1] = btn21;
        boardBtn[0][2] = btn31;
        boardBtn[0][3] = btn41;
        boardBtn[0][4] = btn51;
        boardBtn[0][5] = btn61;

        boardBtn[1][0] = btn12;
        boardBtn[1][1] = btn22;
        boardBtn[1][2] = btn32;
        boardBtn[1][3] = btn42;
        boardBtn[1][4] = btn52;
        boardBtn[1][5] = btn62;

        boardBtn[2][0] = btn13;
        boardBtn[2][1] = btn23;
        boardBtn[2][2] = btn33;
        boardBtn[2][3] = btn43;
        boardBtn[2][4] = btn53;
        boardBtn[2][5] = btn63;

        boardBtn[3][0] = btn14;
        boardBtn[3][1] = btn24;
        boardBtn[3][2] = btn34;
        boardBtn[3][3] = btn44;
        boardBtn[3][4] = btn54;
        boardBtn[3][5] = btn64;

        boardBtn[4][0] = btn15;
        boardBtn[4][1] = btn25;
        boardBtn[4][2] = btn35;
        boardBtn[4][3] = btn45;
        boardBtn[4][4] = btn55;
        boardBtn[4][5] = btn65;

        boardBtn[5][0] = btn16;
        boardBtn[5][1] = btn26;
        boardBtn[5][2] = btn36;
        boardBtn[5][3] = btn46;
        boardBtn[5][4] = btn56;
        boardBtn[5][5] = btn66;

        boardBtn[6][0] = btn17;
        boardBtn[6][1] = btn27;
        boardBtn[6][2] = btn37;
        boardBtn[6][3] = btn47;
        boardBtn[6][4] = btn57;
        boardBtn[6][5] = btn67;

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
            img1.setBackground(bgRed);
            img2.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 2) {
            img2.setBackground(bgRed);
            img3.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 3) {
            img3.setBackground(bgRed);
            img4.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 4) {
            img4.setBackground(bgRed);
            img5.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 5) {
            img5.setBackground(bgRed);
            img6.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 6) {
            img6.setBackground(bgRed);
            img7.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        }

        // Marca que o cabelho voltou uma posicao
        posCabecalho--;
    }

    public void moveRight() {
        if (posCabecalho == 0) {
            img2.setBackground(bgRed);
            img1.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 1) {
            img3.setBackground(bgRed);
            img2.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 2) {
            img4.setBackground(bgRed);
            img3.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 3) {
            img5.setBackground(bgRed);
            img4.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 4) {
            img6.setBackground(bgRed);
            img5.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 5) {
            img7.setBackground(bgRed);
            img6.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        } else if (posCabecalho == 6) {
            // Nao se move pois esta na margem
            // -- apenas para anular o ++ no final
            posCabecalho--;
        }

        // Marca que o cabelho avancou uma posicao
        posCabecalho++;
    }

    public boolean moveDown() {
        boolean edit = false;

        for (int i = 5; i >= 0; i--) {
            if (board[posCabecalho][i] == 0) {
                board[posCabecalho][i] = playerAtual;

                // Marca que ocorreu uma edicao
                edit = true;

                // Sai do for
                i = -1;
            }
        }

        // Verifica se aconteceu alguma alteracao
        if (edit) {
            // Atualiza o tabuleiro
            showBoard();

            // Verifica se venceu
            if (win()) {
                btnLeft.setOnClickListener(null);
                btnRight.setOnClickListener(null);
                btnDown.setOnClickListener(null);

                btnMinimax.setVisibility(View.VISIBLE);
                btnMinimaxAlfaBeta.setVisibility(View.VISIBLE);
                txtWinner.setVisibility(View.VISIBLE);

                btnLeft.setVisibility(View.GONE);
                btnRight.setVisibility(View.GONE);
                btnDown.setVisibility(View.GONE);

                if (playerAtual == 1) {
                    txtWinner.setText("O jogador vermelho venceu!");
                } else {
                    txtWinner.setText("O jogador amarelo venceu!");
                }
            }

            // Troca o jogador atual
            playerAtual = (playerAtual%2)+1;
            return true;
        } else {
            Toast.makeText(this, "Coluna cheia!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void showBoard() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == 1) {
                    boardBtn[i][j].setBackground(bgRed);
                } else if (board[i][j] == 2) {
                    boardBtn[i][j].setBackground(bgYellow);
                }
            }
        }
    }

    public void startGame(final boolean alfaBeta) {
        btnMinimax.setVisibility(View.GONE);
        btnMinimaxAlfaBeta.setVisibility(View.GONE);
        txtWinner.setVisibility(View.GONE);

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
                // Faz o movimento do player 1
                boolean success = moveDown();

                // Faz o movimento do minimax
                if (success){
                    if (alfaBeta) {
                        minimaxAlfaBetaPlay();
                    } else {
                        minimaxPlay();
                    }
                }
            }
        });

        initializeBoard();
        showBoard();
    }

    public void minimaxPlay() {
        int[][] newBoard = board.clone();
        Minimax comp = new Minimax(newBoard,4);
        int c = comp.calcValue();
        drop(c);
    }

    public void minimaxAlfaBetaPlay() {
        int[][] newBoard = board.clone();
        MinimaxAlfaBeta comp = new MinimaxAlfaBeta(newBoard,4);
        int c = comp.calcValue();
        drop(c);
    }

    public void linkScreenElements() {
        // Botoes de acao
        btnLeft = (ImageButton) findViewById(R.id.btnLeft);
        btnDown = (ImageButton) findViewById(R.id.btnDown);
        btnRight = (ImageButton) findViewById(R.id.btnRight);
        btnMinimax = (Button) findViewById(R.id.btnMinimax);
        btnMinimaxAlfaBeta = (Button) findViewById(R.id.btnMinimaxAlfaBeta);

        // TextView de ganhador
        txtWinner = (TextView) findViewById(R.id.txtWinner);

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
                if (board[col][row] != 0 &&
                        board[col][row] == board[col+1][row] &&
                        board[col][row] == board[col+2][row] &&
                        board[col][row] == board[col+3][row])
                {
                    win = true;

                    // Marca a vitoria
                    Drawable bgWin;
                    if (board[col][row] == 1) {
                        bgWin = bgRedDark;
                    } else {
                        bgWin = bgYellowDark;
                    }

                    boardBtn[col][row].setBackground(bgWin);
                    boardBtn[col+1][row].setBackground(bgWin);
                    boardBtn[col+2][row].setBackground(bgWin);
                    boardBtn[col+3][row].setBackground(bgWin);
                }
            }
        }
        //Check for vertical win
        for (int row=0;row<3;row++)
        {
            for (int col = 0;col<7;col++)
            {
                if (board[col][row] != 0 &&
                        board[col][row] == board[col][row+1] &&
                        board[col][row] == board[col][row+2] &&
                        board[col][row] == board[col][row+3])
                {
                    win = true;

                    // Marca a vitoria
                    Drawable bgWin;
                    if (board[col][row] == 1) {
                        bgWin = bgRedDark;
                    } else {
                        bgWin = bgYellowDark;
                    }

                    boardBtn[col][row].setBackground(bgWin);
                    boardBtn[col][row+1].setBackground(bgWin);
                    boardBtn[col][row+2].setBackground(bgWin);
                    boardBtn[col][row+3].setBackground(bgWin);
                }
            }
        }
        //Check for diagonal win (/)
        for (int row=5;row>2;row--)
        {
            for (int col = 0;col<4;col++)
            {
                if (board[col][row] != 0 &&
                        board[col][row] == board[col+1][row-1] &&
                        board[col][row] == board[col+2][row-2] &&
                        board[col][row] == board[col+3][row-3])
                {
                    win = true;

                    // Marca a vitoria
                    Drawable bgWin;
                    if (board[col][row] == 1) {
                        bgWin = bgRedDark;
                    } else {
                        bgWin = bgYellowDark;
                    }

                    boardBtn[col][row].setBackground(bgWin);
                    boardBtn[col+1][row-1].setBackground(bgWin);
                    boardBtn[col+2][row-2].setBackground(bgWin);
                    boardBtn[col+3][row-3].setBackground(bgWin);
                }
            }
        }
        //Check for diagonal win (\)
        for (int row=0;row<3;row++)
        {
            for (int col = 0;col<4;col++)
            {
                if (board[col][row] != 0 &&
                        board[col][row] == board[col+1][row+1] &&
                        board[col][row] == board[col+2][row+2] &&
                        board[col][row] == board[col+3][row+3])
                {
                    win = true;

                    // Marca a vitoria
                    Drawable bgWin;
                    if (board[col][row] == 1) {
                        bgWin = bgRedDark;
                    } else {
                        bgWin = bgYellowDark;
                    }

                    boardBtn[col][row].setBackground(bgWin);
                    boardBtn[col+1][row+1].setBackground(bgWin);
                    boardBtn[col+2][row+2].setBackground(bgWin);
                    boardBtn[col+3][row+3].setBackground(bgWin);
                }
            }
        }
        return win;
    }

    public int drop(int col) {
        int posCabecalhoOld = posCabecalho;
        posCabecalho = col;

        moveDown();

        posCabecalho = posCabecalhoOld;
        if (win()) {
            return -1;
        }

        return 0;
    }
}
