public class NeuralNet {

    int inputValue[][]= {{0,0},{0,1},{1,0},{1,1}}; //inputs (havnt added bias)
    double ouputValue[]={0.0,1.0,1.0,0.0};  //output
    int inputNum=2;  //number of inputs
    int hiddenNum=4;    //number of hidden neuron
    double learningrate=0.2;
    double momentum=0.0;
    double argA=-0.5;      //lower limit
    double argB=0.5;      //upper limit
    double inputWeights[][]=new double[inputNum][hiddenNum]; /*from input to hidden*/
    double hiddenWeights[]=new double[hiddenNum];  /*from hidden to output*/
    double activation[]=new double[hiddenNum+1];   //array to calculate activations
    double output[]=new double[hiddenNum+1];       //array to hold final answers for each input
    double weightChangeToOutput[]=new double[hiddenNum+1]; //array to store weight-change values from hidden to output
    double weightChangeToHidden[]=new double[hiddenNum+1]; //array to store weight-change values from input to hidden
    double sigmoid,sigmoidDerivative,deltaOutput,deltaHidden;

    public NeuralNet(){
        for (int i=0;i<hiddenNum+1;i++){
            weightChangeToOutput[i]=0.0;
        }
    }
    // Initializing random weights
    public void iniWeight(){

        for (int i=0;i<inputNum;i++){
            for (int j=0;j<hiddenNum;j++){
                inputWeights[i][j]=(Math.random()*(argB-argA))+argA;
            }
        }
        for (int i=0;i<hiddenNum;i++){
            hiddenWeights[i]=(Math.random()*(argB-argA))+argA;
        }
       /*  printing the weights
       for (int i=0;i<inputNum;i++){
            for (int j=0;j<hiddenNum;j++){
                System.out.print(inputWeights[i][j]+"  ");;
            }
            System.out.println();
        }
        for (int i=0;i<hiddenNum;i++){
            System.out.print(hiddenWeights[i]+"  ");;
        }  */

    }
    public int train(){
        double error=1;
        int epoch=0;
        //while (error>0.05){
            for (int i=0;i<inputValue.length;i++){
                forwardPropagate(i,inputNum,hiddenNum);
                /*backPropagate(i);*/
            }
            epoch++;


       // }
        return epoch;
    }


    public void forwardPropagate(int a,int in,int out){

        for (int i=0;i<out;i++){
            activation[i]=0.0;
            //calculating summation of weights and input value
            for (int j=0;j<in;j++){
                activation[i]+=inputValue[a][j]*inputWeights[j][i];
            }
            activation[i]=sigmoid(activation[i]); //Activation function
        }
        double answer=0.0;
        //Calculating summation of weights and input from hidden to output
        for (int i=0;i<out;i++){
            answer+=activation[i]*hiddenWeights[i];
        }
        answer=sigmoid(answer); // Activation function
        output[a]=answer; //storing outputs
    }



    public void backPropagate(int a){
        deltaOutput=derivative(output[a])*(ouputValue[a]-output[a]);
        for (int i=0;i<hiddenNum;i++){
            weightChangeToOutput[i]=(learningrate*deltaOutput*activation[i])+(momentum*weightChangeToOutput[i]);
            hiddenWeights[i]+=weightChangeToOutput[i];
        }
        for (int i=0;i<hiddenNum;i++) {
            deltaHidden = derivative(activation[i])*deltaOutput*
        }
    }
    public void calculateError(){

    }

    public double sigmoid(double s){
        sigmoid=1/(1+Math.pow(Math.E,(-s)));
        sigmoidDerivative=derivative(sigmoid);
        return (1/(1+Math.pow(Math.E,(-s))));
    }

    public double derivative(double x){
        return x*(1-x);
    }




    public static void main(String[] args) {
        NeuralNet obj = new NeuralNet();
        obj.iniWeight();
        obj.train();
        /*   printing outputs after forward propagation

        for (int i=0;i<4;i++){
            System.out.println(obj.output[i]);
        }
        */

		//test
    }


};
