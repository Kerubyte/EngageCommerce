package com.example.engagecommerce.presentation.ui.order

/*

@ScreenName(name = "Orders")
class OrdersFragment : RootFragment() {

    private lateinit var binding: FragmentOrdersBinding
    private lateinit var ordersViewModel: OrdersViewModel
    private val orderAdapter = OrderAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_orders,
            container,
            false
        )
        setAnimation()

        ordersViewModel = OrdersViewModel()
        UserCom.getInstance().trackScreen(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewRecyclerOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ordersViewModel.orders.observe(viewLifecycleOwner, {
            orderAdapter.setOrders(it)
        })
    }


}*/
