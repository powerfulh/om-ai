package om;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class vs_AI {
	static Toolkit use_image = Toolkit.getDefaultToolkit();
	static Image player1 = use_image.getImage("p1.png");
	static Image player2 = use_image.getImage("p2.png");
	static boolean turn = true;
	static JFrame frame = new JFrame("오목");
	static JFrame ai = new JFrame("인공지능");
	
	public static void main(String[] args) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setLayout(new GridLayout(13,13));
		frame.setResizable(false);
		ai.setSize(400, 300);
		ai.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ai.setLocation(650, 50);
		ai.setVisible(true);
		JLabel l = new JLabel("대기");
		ai.add(l);
		Onepoint2[] points = new Onepoint2[169];
		boolean change_color = true;
		for(int make_layout = 0; make_layout < points.length; make_layout++) {
			points[make_layout] = new Onepoint2(make_layout);
			points[make_layout].setLayout(new GridLayout(1,1));
			points[make_layout].addMouseListener(new Soloplay(points[make_layout] , frame , l , points));
			if(change_color) {
				points[make_layout].setBackground(Color.black);
				change_color = false;
			}else {
				points[make_layout].setBackground(Color.white);
				change_color = true;
			}
			frame.add(points[make_layout]);
		}
		
		frame.setVisible(true);
	}

}

class Onepoint2 extends JPanel{
	char empty = 'n';
	int h , v;
	public Onepoint2(int a) {
		if(a+1 < 14) {
			v = 1;	h = a + 1;
		} else if(a+1 < 27) {
			v = 2;	h = a + 1 - 13;
		} else if(a+1 < 40) {
			v = 3;	h = a + 1 - 26;
		} else if(a+1 < 53) {
			v = 4;	h = a + 1 - 39;
		} else if(a+1 < 66) {
			v = 5;	h = a + 1 - 52;
		} else if(a+1 < 79) {
			v = 6;	h = a + 1 - 65;
		} else if(a+1 < 92) {
			v = 7;	h = a + 1 - 78;
		} else if(a+1 < 105) {
			v = 8;	h = a + 1 - 91;
		} else if(a+1 < 118) {
			v = 9;	h = a + 1 - 104;
		} else if(a+1 < 131) {
			v = 10;	h = a + 1 - 117;
		} else if(a+1 < 144) {
			v = 11; h = a + 1 - 130;
		} else if(a+1 < 157) {
			v = 12;	h = a + 1 - 143;
		} else if(a+1 < 170) {
			v = 13;	h = a + 1 - 156;
		}
	}
}

class Put extends JPanel{
	ImageIcon p1box = new ImageIcon("p1.png");
	Image newp1 = p1box.getImage();
	public void paint(Graphics g) {
		this.setBackground(Color.gray);
		super.paint(g);
		g.drawImage(newp1, 4, 4, this);
	}
}

class Put2 extends JPanel{
	ImageIcon p2box = new ImageIcon("p2.png");
	Image newp2 = p2box.getImage();
	public void paint(Graphics g) {
		this.setBackground(Color.gray);
		super.paint(g);
		g.drawImage(newp2, 2, 2, this);
	}
}

class Soloplay implements MouseListener{
	Onepoint2 find;
	JFrame frame_find;
	JLabel label_find;
	Onepoint2[] points_find;
	Think t;
	public Soloplay(Onepoint2 f , JFrame frame , JLabel l , Onepoint2[] o) {
		find = f;
		frame_find = frame;
		label_find = l;
		points_find = o;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.isMetaDown()) {
		}else {
			if(find.empty == 'n') {
				if(vs_AI.turn == true) {
					String a = label_find.getText();
					if(a.equals("인공지능 패배")) {
					}else {
						find.empty = 'p';
						find.add(new Put());
						vs_AI.turn = false;
						frame_find.setVisible(true);
						t = new Think(label_find , points_find);
						find.removeMouseListener(this);
					}
				}
			}
		}
	}
	public int coodinate(int a , int b) {
		return a + (b-1) * 13 - 1;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}

class Think{
	char mod;
	JLabel thinking;
	public Think(JLabel l , Onepoint2[] o) {
		thinking = l;
		mod = 'a';
		thinking.setText("생각을 시작한다");
		for(int explore = 0; explore < 169; explore++) {
			if(o[explore].empty == 'p') {
				try {
				if(o[explore].h > 2) {
					if(o[explore].h < 12) {
						if(o[explore].v > 2) {
							if(o[explore].v < 12) {
								int leftup = coodinate(o[explore].h - 1 , o[explore].v - 1);
								int leftup2 = coodinate(o[explore].h - 2 , o[explore].v - 2);
								int leftdown = coodinate(o[explore].h - 1 , o[explore].v + 1);
								int leftdown2 = coodinate(o[explore].h - 2 , o[explore].v + 2);
								int rightdown = coodinate(o[explore].h + 1 , o[explore].v + 1);
								int rightdown2 = coodinate(o[explore].h + 2 , o[explore].v + 2);
								int rightup = coodinate(o[explore].h + 1 , o[explore].v - 1);
								int rightup2 = coodinate(o[explore].h + 2 , o[explore].v - 2);
								int left = coodinate(o[explore].h - 1 , o[explore].v);
								int left2 = coodinate(o[explore].h - 2 , o[explore].v);
								int up = coodinate(o[explore].h , o[explore].v - 1);
								int up2 = coodinate(o[explore].h , o[explore].v - 2);
								int right = coodinate(o[explore].h + 1 , o[explore].v);
								int right2 = coodinate(o[explore].h + 2 , o[explore].v);
								int down = coodinate(o[explore].h, o[explore].v + 1);
								int down2 = coodinate(o[explore].h, o[explore].v + 2);
								if(o[leftup].empty == 'p') {
									if(o[rightdown].empty == 'p') {
										if(o[leftup2].empty == 'p') {
											if(o[rightdown2].empty == 'p') {
												System.out.println("승리");
												thinking.setText("인공지능 패배");
												mod = 'b';
												break;
											}else if(o[rightdown2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[rightdown2].add(new Put2());
												o[rightdown2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}else if(o[rightdown2].empty == 'p') {
											if(o[leftup2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[leftup2].add(new Put2());
												o[leftup2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}
										if(o[leftup2].empty == 'n') {
											if(o[rightdown2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[leftup2].add(new Put2());
												o[leftup2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}
									}
								}
								if(o[rightup].empty == 'p') {
									if(o[leftdown].empty == 'p') {
										if(o[rightup2].empty == 'p') {
											if(o[leftdown2].empty == 'p') {
												System.out.println("승리");
												thinking.setText("인공지능 패배");
												mod = 'b';
												break;
											}else if(o[leftdown2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[leftdown2].add(new Put2());
												o[leftdown2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}else if(o[leftdown2].empty == 'p') {
											if(o[rightup2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[rightup2].add(new Put2());
												o[rightup2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}
										if(o[rightup2].empty == 'n') {
											if(o[leftdown2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[rightup2].add(new Put2());
												o[rightup2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}
									}
								}
								if(o[up].empty == 'p') {
									if(o[down].empty == 'p') {
										if(o[up2].empty == 'p') {
											if(o[down2].empty == 'p') {
												System.out.println("승리");
												thinking.setText("인공지능 패배");
												mod = 'b';
												break;
											}else if(o[down2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[down2].add(new Put2());
												o[down2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}else if(o[down2].empty == 'p') {
											if(o[up2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[up2].add(new Put2());
												o[up2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}
										if(o[up2].empty == 'n') {
											if(o[down2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[up2].add(new Put2());
												o[up2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}
									}
								}
								if(o[left].empty == 'p') {
									if(o[right].empty == 'p') {
										if(o[left2].empty == 'p') {
											if(o[right2].empty == 'p') {
												System.out.println("승리");
												thinking.setText("인공지능 패배");
												mod = 'b';
												break;
											}else if(o[right2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[right2].add(new Put2());
												o[right2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}else if(o[right2].empty == 'p') {
											if(o[left2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[left2].add(new Put2());
												o[left2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}
										if(o[left2].empty == 'n') {
											if(o[right2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[left2].add(new Put2());
												o[left2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}
									}
								}
							}
						}
					}
				}
				}catch(Exception e) {
					continue;
				}
			}
		}
		if(mod == 'a') {
			for(int explore = 0; explore < 169; explore++) {
				if(o[explore].empty == 'p') {
					int left = coodinate(o[explore].h - 1 , o[explore].v);
					int left2 = coodinate(o[explore].h - 2 , o[explore].v);
					int up = coodinate(o[explore].h , o[explore].v - 1);
					int up2 = coodinate(o[explore].h , o[explore].v - 2);
					int right = coodinate(o[explore].h + 1 , o[explore].v);
					int right2 = coodinate(o[explore].h + 2 , o[explore].v);
					int down = coodinate(o[explore].h, o[explore].v + 1);
					int down2 = coodinate(o[explore].h, o[explore].v + 2);
					try {
						if(o[explore].v > 2) {
							if(o[explore].v < 12) {
								if(o[up].empty == 'p') {
									if(o[down].empty == 'p') {
										if(o[up2].empty == 'p') {
											if(o[down2].empty == 'p') {
												System.out.println("승리");
												thinking.setText("인공지능 패배");
												mod = 'b';
												break;
											}else if(o[down2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[down2].add(new Put2());
												o[down2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}else if(o[down2].empty == 'p') {
											if(o[up2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[up2].add(new Put2());
												o[up2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}
										if(o[up2].empty == 'n') {
											if(o[down2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[up2].add(new Put2());
												o[up2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}
									}
								}
							}
						}
						if(o[explore].h > 2) {
							if(o[explore].h < 12) {
								if(o[left].empty == 'p') {
									if(o[right].empty == 'p') {
										if(o[left2].empty == 'p') {
											if(o[right2].empty == 'p') {
												System.out.println("승리");
												thinking.setText("인공지능 패배");
												mod = 'b';
												break;
											}else if(o[right2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[right2].add(new Put2());
												o[right2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}else if(o[right2].empty == 'p') {
											if(o[left2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[left2].add(new Put2());
												o[left2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}
										if(o[left2].empty == 'n') {
											if(o[right2].empty == 'n') {
												thinking.setText("막을곳 발견");
												o[left2].add(new Put2());
												o[left2].empty = 'a';
												mod = 'b';
												vs_AI.turn = true;
												break;
											}
										}
									}
								}
							}
						}
					}catch(Exception e) {
						continue;
					}
				}
			}
		}
		if(mod == 'a') {
			for(int explore = 0; explore < 169; explore++) {
				if(o[explore].empty == 'p') {
					try {
						int leftup = coodinate(o[explore].h - 1 , o[explore].v - 1);
						int leftdown = coodinate(o[explore].h - 1 , o[explore].v + 1);
						int left = coodinate(o[explore].h - 1 , o[explore].v);
						int up = coodinate(o[explore].h , o[explore].v - 1);
						int rightup = coodinate(o[explore].h + 1 , o[explore].v - 1);
						int right = coodinate(o[explore].h + 1 , o[explore].v);
						int rightdown = coodinate(o[explore].h + 1 , o[explore].v + 1);
						int down = coodinate(o[explore].h, o[explore].v + 1);
						if(o[up].empty == 'n') {
							thinking.setText("3열 공격이 발견되지 않음");
							o[up].add(new Put2());
							o[up].empty = 'a';
							mod = 'b';
							vs_AI.turn = true;
							break;
						}
						if(o[down].empty == 'n') {
							thinking.setText("3열 공격이 발견되지 않음");
							o[down].add(new Put2());
							o[down].empty = 'a';
							mod = 'b';
							vs_AI.turn = true;
							break;
						}
						if(o[left].empty == 'n') {
							thinking.setText("3열 공격이 발견되지 않음");
							o[left].add(new Put2());
							o[left].empty = 'a';
							mod = 'b';
							vs_AI.turn = true;
							break;
						}
						if(o[right].empty == 'n') {
							thinking.setText("3열 공격이 발견되지 않음");
							o[right].add(new Put2());
							o[right].empty = 'a';
							mod = 'b';
							vs_AI.turn = true;
							break;
						}
						if(o[leftup].empty == 'n') {
							thinking.setText("3열 공격이 발견되지 않음");
							o[leftup].add(new Put2());
							o[leftup].empty = 'a';
							mod = 'b';
							vs_AI.turn = true;
							break;
						}
						if(o[rightup].empty == 'n') {
							thinking.setText("3열 공격이 발견되지 않음");
							o[rightup].add(new Put2());
							o[rightup].empty = 'a';
							mod = 'b';
							vs_AI.turn = true;
							break;
						}
						if(o[leftdown].empty == 'n') {
							thinking.setText("3열 공격이 발견되지 않음");
							o[leftdown].add(new Put2());
							o[leftdown].empty = 'a';
							mod = 'b';
							vs_AI.turn = true;
							break;
						}
						if(o[rightdown].empty == 'n') {
							thinking.setText("3열 공격이 발견되지 않음");
							o[rightdown].add(new Put2());
							o[rightdown].empty = 'a';
							mod = 'b';
							vs_AI.turn = true;
							break;
						}
					}catch(Exception e) {
						continue;
					}
				}
			}
		}
		if(mod == 'a') {
			thinking.setText("유효수를 찾기 못함");
			for(int explore = 0; explore < 169; explore++) {
				if(o[explore].empty == 'n') {
					o[explore].add(new Put2());
					o[explore].empty = 'a';
					vs_AI.turn = true;
					break;
				}
			}
		}
		vs_AI.turn = cheakwin(o);
		vs_AI.frame.repaint();
		vs_AI.ai.repaint();
		vs_AI.frame.setVisible(true);
	}
	public int coodinate(int a , int b) {
		return a + (b-1) * 13 - 1;
	}
	public boolean cheakwin(Onepoint2[] o) {
		boolean keepgoing = true;
		for(int explore = 0; explore < 169; explore++) {
			if(o[explore].empty == 'a') {
				if(o[explore].h > 2) {
					if(o[explore].h < 12) {
						if(o[explore].v > 2) {
							if(o[explore].v < 12) {
								int leftup = coodinate(o[explore].h - 1 , o[explore].v - 1);
								int leftup2 = coodinate(o[explore].h - 2 , o[explore].v - 2);
								int leftdown = coodinate(o[explore].h - 1 , o[explore].v + 1);
								int leftdown2 = coodinate(o[explore].h - 2 , o[explore].v + 2);
								int left = coodinate(o[explore].h - 1 , o[explore].v);
								int left2 = coodinate(o[explore].h - 2 , o[explore].v);
								int up = coodinate(o[explore].h , o[explore].v - 1);
								int up2 = coodinate(o[explore].h , o[explore].v - 2);
								int rightup = coodinate(o[explore].h + 1 , o[explore].v - 1);
								int rightup2 = coodinate(o[explore].h + 2 , o[explore].v - 2);
								int right = coodinate(o[explore].h + 1 , o[explore].v);
								int right2 = coodinate(o[explore].h + 2 , o[explore].v);
								int rightdown = coodinate(o[explore].h + 1 , o[explore].v + 1);
								int rightdown2 = coodinate(o[explore].h + 2 , o[explore].v + 2);
								int down = coodinate(o[explore].h, o[explore].v + 1);
								int down2 = coodinate(o[explore].h, o[explore].v + 2);
								if(o[leftup].empty == 'a') {
									if(o[rightdown].empty == 'a') {
										if(o[leftup2].empty == 'a') {
											if(o[rightdown2].empty == 'a') {
												System.out.println(o[explore].h + " " + o[explore].v);
												keepgoing = false;
												System.out.println("패배");
												thinking.setText("인공지능 승리");
												break;
											}
										}
									}
								}
								if(o[rightup].empty == 'a') {
									if(o[leftdown].empty == 'a') {
										if(o[rightup2].empty == 'a') {
											if(o[leftdown2].empty == 'a') {
												keepgoing = false;
												System.out.println("패배");
												thinking.setText("인공지능 승리");
												break;
											}
										}
									}
								}
								if(o[left].empty == 'a') {
									if(o[right].empty == 'a') {
										if(o[left2].empty == 'a') {
											if(o[right2].empty == 'a') {
												keepgoing = false;
												System.out.println("패배");
												thinking.setText("인공지능 승리");
												break;
											}
										}
									}
								}
								if(o[up].empty == 'a') {
									if(o[down].empty == 'a') {
										if(o[up2].empty == 'a') {
											if(o[down2].empty == 'a') {
												keepgoing = false;
												System.out.println("패배");
												thinking.setText("인공지능 승리");
												break;
											}
										}
									}
								}
								
							}
						}
					}
				}
			}
		}
		return keepgoing;
	}
}