public class NeuralNet {

    int inputValue[][]= {{0,0,1},{0,1,1},{1,0,1},{1,1,1}}; //inputs 
    double ouputValue[]={0.0,1.0,1.0,0.0};  //output
    int inputNum=2;  //number of inputs
    int hiddenNum=4;    //number of hidden neuron
    double learningrate=0.2;
    double momentum=0.0;
    double argA=-0.5;      //lower limit
    double argB=0.5;      //upper limit
    double inputWeights[][]=new double[inputNum+1][hiddenNum+1]; /*from input to hidden*/
    double hiddenWeights[]=new double[hiddenNum+1];  /*from hidden to output*/
    double activation[]=new double[hiddenNum+1];   //array to calculate activations
    double output[]=new double[hiddenNum+1];       //array to hold final answers for each input
    double weightChangeToOutput[]=new double[hiddenNum+1]; //array to store weight-change values from hidden to output
    double weightChangeToHidden[][]=new double[inputNum+1][hiddenNum+1]; //array to store weight-change values from input to hidden
    double deltaHidden[]=new double[hiddenNum+1];  //array to store delta values for each hidden layer neuron
    double sigmoid,sigmoidDerivative,deltaOutput;

    public NeuralNet(){
        for (int i=0;i<hiddenNum+1;i++){
            weightChangeToOutput[i]=0.0;
        }
        activation[hiddenNum]=1.0;
    }
    // Initializing random weights
    public void iniWeight(){

        for (int i=0;i<inputNum+1;i++){
            for (int j=0;j<hiddenNum+1;j++){
                inputWeights[i][j]=(Math.random()*(argB-argA))+argA;
            }
        }
        for (int i=0;i<hiddenNum+1;i++){
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
        }
        System.out.println();
        System.out.println(); */

    }
    public int train(){
        double error=1;
        int epoch=0;
        while (error>0.05){
            for (int i=0;i<inputValue.length;i++){
                forwardPropagate(i,inputNum,hiddenNum);
                backPropagate(i);
            }
            error=calculateError();
            System.out.println(error);
            epoch++;


       }
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
        for (int i=0;i<out+1;i++){
            answer+=activation[i]*hiddenWeights[i];
        }
        answer=sigmoid(answer); // Activation function
        output[a]=answer; //storing outputs
    }



    public void backPropagate(int a){
        deltaOutput=derivative(output[a])*(ouputValue[a]-output[a]);   //delta for output
        // calculating weight change and updating corresponding weights
        for (int i=0;i<hiddenNum+1;i++){
            weightChangeToOutput[i]=(learningrate*deltaOutput*activation[i])+(momentum*weightChangeToOutput[i]);
            hiddenWeights[i]+=weightChangeToOutput[i];
        }
        // calculating delta for hidden neurons and updating weights       
        for (int i=0;i<hiddenNum+1;i++) {
            deltaHidden[i] = derivative(activation[i]) * deltaOutput * hiddenWeights[i];
            for (int j = 0; j < inputNum+1; j++) {
                weightChangeToHidden[j][i] = (learningrate * deltaHidden[i] * inputValue[a][j])+(momentum * weightChangeToOutput[i]);
                inputWeights[j][i]+=weightChangeToHidden[j][i];
            }
        }
        /* for (int i=0;i<inputNum;i++){
            for (int j=0;j<hiddenNum;j++){
                System.out.print(inputWeights[i][j]+"  ");;
            }
            System.out.println();
        }
        for (int i=0;i<hiddenNum;i++){
            System.out.print(hiddenWeights[i]+"  ");;
        }
        System.out.println();System.out.println(); */
    }
    public double calculateError(){
        double totalErr=0.0;
        // err=(C^2-y^2)/2
        for (int i=0;i<ouputValue.length;i++){
            totalErr+=Math.pow(ouputValue[i]-output[i],2);
        }
        return totalErr/2;

    }

    public double sigmoid(double s){
        sigmoid=((argB-argA)/(1+Math.pow(Math.E,(-s))))+argA;
        sigmoidDerivative=derivative(sigmoid);
        return (1/(1+Math.pow(Math.E,(-s))));
    }

    public double derivative(double x){

        return x*(1-x);
    }

    public static void main(String[] args) {
        NeuralNet obj = new NeuralNet();
        obj.iniWeight();
        System.out.println(obj.train());
        /*   printing outputs after forward propagation

        for (int i=0;i<4;i++){
            System.out.println(obj.output[i]);
        }
        */


    }


};
